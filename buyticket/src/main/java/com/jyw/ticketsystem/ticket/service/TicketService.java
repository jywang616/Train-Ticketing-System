package com.jyw.ticketsystem.ticket.service;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.ticket.domain.*;
import com.jyw.ticketsystem.ticket.mapper.*;
import com.jyw.ticketsystem.ticket.req.QueryReq;
import com.jyw.ticketsystem.ticket.req.TicketSaveReq;
import com.jyw.ticketsystem.ticket.resp.NonStopQueryResp;
import com.jyw.ticketsystem.ticket.resp.StopQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TicketService {
    private static final Logger LOG = LoggerFactory.getLogger(TicketService.class);

    @Resource
    private CityStationMapper cityStationMapper;

    @Resource
    private StationMapper stationMapper;

    @Resource
    private CarriageMapper carriageMapper;

    @Resource
    private GraphMapper graphMapper;
    @Resource
    private TrainMapper trainMapper;
    @Resource
    private TicketMapper ticketMapper;

    public void save(TicketSaveReq req){

        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = tempDate.format(new Date());

        Ticket ticket=BeanUtil.copyProperties(req,Ticket.class);
        ticket.setId(SnowUtil.getSnowflakeNextId());
        ticket.setDailyTrainTicketId(SnowUtil.getSnowflakeNextId());
        ticket.setCreateTime(now);
        ticket.setUpdateTime(now);
        ticketMapper.saveTicket(ticket);
    }
    public PageResp<NonStopQueryResp> NonStopTrainList(QueryReq req) throws ParseException {

        //查找匹配列车
        List<TrainInfo> infos=findtrainbycity(req.getStart_city(), req.getEnd_city(), req.getStart_date(), req.getEnd_date());

        //List<TrainInfo>infos=new ArrayList<>();
        //infos.add(new TrainInfo(123,"00","11", "2023-06-21","2023-06-22",1,2));
        //PageHelper放在后面！！
        PageHelper.startPage(req.getPage(), req.getSize());
        PageInfo<TrainInfo> pageInfo=new PageInfo<>(infos);
        List<NonStopQueryResp> list=BeanUtil.copyToList(infos, NonStopQueryResp.class);
        PageResp<NonStopQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public PageResp<StopQueryResp> StopTrainList(QueryReq req)throws ParseException{
        //建图
        //1获得城市 2获得每个城市的站点 3 遍历当日所有车次,根据站点获得对应城市并将其存储起来到Graph中
        Map<String,Map<String,Integer>>graphs=new HashMap<>();
        graphs=graphMapper.getGraphs(req.getStart_date());
        if(graphs.size()==0){
            graphs=createGraph(req.getStart_date());
            graphMapper.saveGraph(graphs,req.getStart_date());
        }
        LOG.info("graphs"+graphs.toString());

        //算法一：迪杰斯特拉
        //re_route:从后往前是城市路径
        List<String> re_path=Dijkstra(req,graphs);
        List<StopTrainInfo> stopTrainInfos=new ArrayList<>();
        int index=re_path.size()-1;//得到当日列车，有位置！
        if(re_path.size()>2){
            List<TrainInfo> start_train=findtrainbycity(re_path.get(index),re_path.get(index-1), req.getStart_date(), req.getStart_date());
            LOG.info(start_train.toString());
            stopTrainInfos.addAll(CreateStopTrainInfo(start_train,0));
            for(int j=0;j<stopTrainInfos.size();j++){ //每人走出一条路
                StopTrainInfo stopTrainInfo=findpath(re_path,index-1,stopTrainInfos.get(j), req.getEnd_date());
                LOG.info(String.valueOf(stopTrainInfo));
                if(stopTrainInfo.getInfos().size()<re_path.size()-1){
                    LOG.info("there");
                    stopTrainInfos.remove(j);
                    j--;
                }else{
                    TrainInfo nextinfo=stopTrainInfo.getInfos().get(stopTrainInfo.getInfos().size()-1);
                    LOG.info("here"+String.valueOf(nextinfo));
                    String end_time= nextinfo.getEnd_time();
                    String end_station=nextinfo.getEnd_station();
                    stopTrainInfo.setEnd_time(end_time);
                    stopTrainInfo.setEnd_station(end_station);
                }
            }
        }
        LOG.info(stopTrainInfos.toString());

        //算法二：暴力算法
        List<String>transfer_cities=BruteForce(req,graphs);
        List<StopTrainInfo> tr_stopTrainInfos=new ArrayList<>();
        if(transfer_cities.size()==0){}
        else{
            for(String transfer_city:transfer_cities){
                List<TrainInfo>tr_start_train=findtrainbycity(req.getStart_city(), transfer_city,req.getStart_date(), req.getEnd_date());
                tr_stopTrainInfos.addAll(CreateStopTrainInfo(tr_start_train,1));
                for(int i=0;i<tr_stopTrainInfos.size();++i){
                    TrainInfo nextinfo=findTrainbyStartStation(tr_stopTrainInfos.get(i), req.getEnd_city(), req.getEnd_date());
                    tr_stopTrainInfos.get(i).getInfos().add(nextinfo);

                    //若没有下一步，则删除
                    if(nextinfo==null){
                        tr_stopTrainInfos.remove(i);
                        i--;
                        continue;
                    }
                    //设置结束信息
                    String end_time= nextinfo.getEnd_time();
                    String end_station=nextinfo.getEnd_station();
                    tr_stopTrainInfos.get(i).setEnd_time(end_time);
                    tr_stopTrainInfos.get(i).setEnd_station(end_station);
                }
            }
        }
        LOG.info(tr_stopTrainInfos.toString());

        stopTrainInfos.addAll(tr_stopTrainInfos);

        for(int i=0;i<stopTrainInfos.size();++i)
            stopTrainInfos.get(i).setId(i);

        PageHelper.startPage(req.getPage(), req.getSize());
        PageInfo<StopTrainInfo> pageInfo=new PageInfo<>(stopTrainInfos);
        List<StopQueryResp> list=BeanUtil.copyToList(stopTrainInfos, StopQueryResp.class);
        PageResp<StopQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
    public PageResp<StopQueryResp> DijkstraTrainList(QueryReq req) throws ParseException {

        //建图
        //1获得城市 2获得每个城市的站点 3 遍历当日所有车次,根据站点获得对应城市并将其存储起来到Graph中
        Map<String,Map<String,Integer>>graphs=new HashMap<>();
        graphs=graphMapper.getGraphs(req.getStart_date());
        if(graphs.size()==0){
            graphs=createGraph(req.getStart_date());
            graphMapper.saveGraph(graphs,req.getStart_date());
        }

        //算法一：迪杰斯特拉
        //re_route:从后往前是城市路径
        List<String> re_path=Dijkstra(req,graphs);
        List<StopTrainInfo> stopTrainInfos=new ArrayList<>();
        int index=re_path.size()-1;//得到当日列车，有位置！
        if(re_path.size()>2){
            List<TrainInfo> start_train=findtrainbycity(re_path.get(index),re_path.get(index-1), req.getStart_date(), req.getStart_date());
            stopTrainInfos.addAll(CreateStopTrainInfo(start_train,0));
            for(int j=0;j<stopTrainInfos.size();j++){ //每人走出一条路
                StopTrainInfo stopTrainInfo=findpath(re_path,index-1,stopTrainInfos.get(j), req.getEnd_date());
                if(stopTrainInfo.getInfos().size()<re_path.size()-1){
                    stopTrainInfos.remove(j);
                    j--;
                }else{
                    TrainInfo nextinfo=stopTrainInfo.getInfos().get(stopTrainInfo.getInfos().size()-1);
                    String end_time= nextinfo.getEnd_time();
                    String end_station=nextinfo.getEnd_station();
                    stopTrainInfo.setEnd_time(end_time);
                    stopTrainInfo.setEnd_station(end_station);
                }
            }
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        PageInfo<StopTrainInfo> pageInfo=new PageInfo<>(stopTrainInfos);
        List<StopQueryResp> list=BeanUtil.copyToList(stopTrainInfos, StopQueryResp.class);
        PageResp<StopQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
    private StopTrainInfo findpath(List<String>re_path,int index,StopTrainInfo stopTrainInfo,String end_date){
        if(index==0) return stopTrainInfo;
        String start_city=re_path.get(index);
        String end_city=re_path.get(index-1);
        //若info为空，则说明该start_station与next_city不匹配，寻找该列车的下一个匹配站点；
        //获得该列车与城市匹配站点
        TrainInfo trainInfo=stopTrainInfo.getInfos().get(stopTrainInfo.getInfos().size()-1);
        List<String>start_stations=getTrainCityStation(start_city,trainInfo);
        TrainInfo nextinfo=null;
        for(String start_station:start_stations){
            nextinfo=findTrainbyStartStation(stopTrainInfo,end_city,end_date);
            LOG.info("next"+nextinfo);
            if(nextinfo==null){
            }else{//说明找到了下一个站点了
                stopTrainInfo.getInfos().add(nextinfo);
                stopTrainInfo=findpath(re_path,index-1,stopTrainInfo,end_date);
                if(stopTrainInfo.getInfos().size()==re_path.size()-1) return stopTrainInfo;
            }
        }
        if (nextinfo==null){//说明此路不通
            stopTrainInfo.getInfos().remove(stopTrainInfo.getInfos().size()-1);
            return stopTrainInfo;
        }
        return stopTrainInfo;
    }
    private List<String> getTrainCityStation(String start_city,TrainInfo traininfo){
        int i=traininfo.getStart_NO()+1;
        List<String>stations=new ArrayList<>();
        while(true){
            String station_name=stationMapper.getStationName(traininfo.getTraincode(),i);
            if(station_name==null) break;
            String city=cityStationMapper.matchCity(station_name);
            if(city==null) break;
            if(city.equals(start_city)){
                stations.add(station_name);
            }
            i+=1;
        }
        return stations;
    }
    public PageResp<StopQueryResp> BruteForceTrainList(QueryReq req) throws ParseException {

        //建图
        //1获得城市 2获得每个城市的站点 3 遍历当日所有车次,根据站点获得对应城市并将其存储起来到Graph中
        Map<String,Map<String,Integer>>graphs=new HashMap<>();
        graphs=graphMapper.getGraphs(req.getStart_date());
        if(graphs.size()==0){
            graphs=createGraph(req.getStart_date());
            graphMapper.saveGraph(graphs,req.getStart_date());
        }

        //算法二：暴力算法
        List<String>transfer_cities=BruteForce(req,graphs);
        List<StopTrainInfo> tr_stopTrainInfos=new ArrayList<>();
        if(transfer_cities.size()==0){}
        else{
            for(String transfer_city:transfer_cities){
                List<TrainInfo>tr_start_train=findtrainbycity(req.getStart_city(), transfer_city,req.getStart_date(), req.getEnd_date());
                tr_stopTrainInfos.addAll(CreateStopTrainInfo(tr_start_train,1));
                for(int i=0;i<tr_stopTrainInfos.size();++i){
                    TrainInfo nextinfo=findTrainbyStartStation(tr_stopTrainInfos.get(i), req.getEnd_city(), req.getEnd_date());
                    tr_stopTrainInfos.get(i).getInfos().add(nextinfo);

                    //若没有下一步，则删除
                    if(nextinfo==null){
                        tr_stopTrainInfos.remove(i);
                        i--;
                        continue;
                    }
                    //设置结束信息
                    String end_time= nextinfo.getEnd_time();
                    String end_station=nextinfo.getEnd_station();
                    tr_stopTrainInfos.get(i).setEnd_time(end_time);
                    tr_stopTrainInfos.get(i).setEnd_station(end_station);
                }
            }
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        PageInfo<StopTrainInfo> pageInfo=new PageInfo<>(tr_stopTrainInfos);
        List<StopQueryResp> list=BeanUtil.copyToList(tr_stopTrainInfos, StopQueryResp.class);
        PageResp<StopQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
    private Map<String,Map<String,Integer>> createGraph(String date) throws ParseException {
        Map<String,Map<String,Integer>>map_graph=new HashMap<>();
        List<Train> daily_trains=trainMapper.getdailytrain(date);
        Map<String,String> station_city=cityStationMapper.getmap();

        //将选定区域内的列车都遍历完
        //有无车&有无座位
        for(Train daily_train:daily_trains){
            String traincode=daily_train.getTraincode();
            List<Station> stations=stationMapper.getAllstation(traincode);
            for(int i=1;i<stations.size();++i){
                String end_city=station_city.get(stations.get(i).getName());
                int duration=0;
                for(int j=i-1;j>=0;--j){
                    String start_city=station_city.get(stations.get(j).getName());
                    if(start_city==end_city){
                        continue;
                    }
                    duration+=getduration(stations.get(j).getStart_time(),stations.get(i).getStart_time());
                    int[] seats= carriageMapper.getTicketLeft(new TrainInfo(traincode,j,i));
                    if(seats[0]>0 || seats[1] >0 ||seats[2]>0||seats[3]>0){ //有位置
                        if(map_graph.containsKey(start_city)){
                            Map<String,Integer> re=map_graph.get(start_city);
                            if(re.containsKey(end_city)){   //已存在该城市对照
                                int diff=re.get(end_city);
                                if(duration<diff){
                                    re.replace(end_city,diff,duration);
                                    //看map_graph是否也需要
                                }
                            }else{
                                re.put(end_city,duration);
                            }

                        }else{
                            Map<String,Integer>insert=new HashMap<>();
                            insert.put(end_city,duration);
                            map_graph.put(start_city,insert);
                        }
                    }

                }
            }
        }

//        //生成graph
//        List<Graph> graphs=new ArrayList<>();
//        //DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
//        //DateTime.parse(time,format)
//        for(String start_city:map_graph.keySet()){
//            Map<String,Integer>dmap_graph=map_graph.get(start_city);
//            for(String end_city:dmap_graph.keySet()){
//                Graph graph=new Graph(start_city,end_city,dmap_graph.get(end_city),time);
//                graphs.add(graph);
//            }
//        }

        return map_graph;
    }
    private List<StopTrainInfo> CreateStopTrainInfo(List<TrainInfo> trainInfos,int type){
        List<StopTrainInfo> stinfos=new ArrayList<>();
        for(TrainInfo info:trainInfos){
            List<TrainInfo> tinfos=new ArrayList<>();
            tinfos.add(info);
            //String start_station,String end_station,String start_time,String end_time,
            StopTrainInfo stinfo=new StopTrainInfo(info.getStart_station(), info.getStart_time(), tinfos,type);
            stinfos.add(stinfo);
        }
        return stinfos;
    }
    private List<String> Dijkstra(QueryReq req,Map<String,Map<String,Integer>>graphs){
        String start_city= req.getStart_city();
        List<String>city=cityStationMapper.getCity();
        int n=cityStationMapper.getCityNum();
        int start_index=city.indexOf(start_city);
        int end_index=city.indexOf(req.getEnd_city());
        int []dis=new int[n];
        int []father=new int[n];
        int[]book=new int[n];

        for (int i=0;i<n;++i){
            dis[i]=999999;
            book[i]=0;
            father[i]=-1;
        }

        Map<String,Integer>graph=graphs.get(start_city);
        for(String end_city:graph.keySet()){
            int i=city.indexOf(end_city);
            dis[i]=graph.get(end_city);
            father[i]=start_index;
        }
        book[start_index]=1;
        dis[start_index]=0;
        father[start_index]=start_index;

        for(int i=1;i<n;++i){
            int min=99999;
            int index=-1;
            for(int j=0;j<n;++j){
                if(book[j]==0&&dis[j]<min){
                    min=dis[j];
                    index=j;
                }
            }
            if(index==-1) break;
            book[index]=1;
            for(int k=0;k<n;++k){
                int val=999999;
                if(graphs.containsKey(city.get(index))) {
                    Map<String, Integer> x1 = graphs.get(city.get(index));
                    if (x1.containsKey(city.get(k))) {
                        val = x1.get(city.get(k));
                    }
                }

                if(dis[k]>dis[index]+val){
                    dis[k]=dis[index]+val;
                    father[k]=index;
                }
            }
        }

        List<String>re_route=new ArrayList<>();
        re_route.add(req.getEnd_city());
        int index=father[end_index];
        while(index!=start_index){
            re_route.add(city.get(index));
            index=father[index];
        }
        re_route.add(req.getStart_city());
        return re_route;
    }
    private List<String> BruteForce(QueryReq req,Map<String,Map<String,Integer>>graphs){

        String city1,city2;
        Integer val1,val2;
        val1=99999;val2=99999;city1="";city2="";
        //若没有呢
        if(graphs.containsKey(req.getStart_city())){
            Map<String,Integer>map1=graphs.get(req.getStart_city());
            for(String transfer_city: map1.keySet()){
                Map<String,Integer>map2=graphs.get(transfer_city);
                if(map2.containsKey(req.getEnd_city())){
                    Integer val= map2.get(req.getEnd_city());
                    if(val<val1){
                        val2=val1;val1=val;city2=city1;city1=transfer_city;
                    }else {
                        if(val<val2){
                            val2=val;city2=transfer_city;
                        }
                    }
                }

            }
            List<String>cities=new ArrayList<>();
            if(!city1.isEmpty()) cities.add(city1);
            if(!city2.isEmpty()) cities.add(city2);

            return cities;
        }
        return null;
    }
    //根据出发城市、目的城市、出发日期进行匹配列车的查找
    private List<TrainInfo> findtrainbycity(String start_city,String end_city,String start_date,String end_date) throws ParseException {
        //不能有重复的列车&&列车一定要有位置？？
        List<String>start_stations=cityStationMapper.getstation(start_city);
//        LOG.info(start_stations.toString());
//        LOG.info("\n");
        List<String>end_stations=cityStationMapper.getstation(end_city);
//        LOG.info(end_stations.toString());
//        LOG.info("\n");
        List<TrainInfo> infos = stationMapper.gettraininfo(start_stations,end_stations,start_date,end_date);
//        LOG.info(infos.toString());
//        LOG.info("\n");
        for(int i=0;i<infos.size();++i){
            TrainInfo info=(TrainInfo) infos.get(i);
            int[] ticket=carriageMapper.getTicketLeft(info);
            info.setFirstClass(ticket[0]);
            info.setSecondClass(ticket[1]);
            info.setRW(ticket[2]);
            info.setYW(ticket[3]);
            int duration=getduration(info.getStart_time(), info.getEnd_time());
            info.setDuration(duration);
        }
        //LOG.info(infos.toString());
        //LOG.info("\n");
        return infos;
    }
    private int getduration(String start_time,String end_time) throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date start=df.parse(start_time);
        Date end=df.parse(end_time);
        long diff=end.getTime()-start.getTime();//毫秒
        int minutes= (int) (diff/(1000*60));
        return minutes;

//        long diff = d1.getTime() - d2.getTime();//这样得到的差值是毫秒级别
//        long days = diff / (1000 * 60 * 60 * 24);
//
//        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//        System.out.println("结果:\t"+days+"天"+hours+"小时"+minutes+"分");

    }
    private TrainInfo findTrainbyStartStation(StopTrainInfo stopTrainInfo,String end_city,String end_date){
        //一定有位置！！
        int index=stopTrainInfo.getInfos().size()-1;
        TrainInfo info=stopTrainInfo.getInfos().get(index);
        List<String>end_stations=cityStationMapper.getstation(end_city);
        LOG.info("endstation"+end_stations.toString());

        //假设start_station确定
        //哪个点出发，几点到（-30min）
        //获得城市之间的列车，看顺序是否合适，如时间少于2h，则将最近的列车加入其中
        List<TrainInfo> next_infos = stationMapper.getnexttrain(info.getEnd_station(),end_stations,info.getEnd_time(),end_date);
        LOG.info("info:"+next_infos);
        TrainInfo nextinfo=new TrainInfo();
        for(int i=0;i<next_infos.size();++i){
            nextinfo=(TrainInfo) next_infos.get(i);
            int[] ticket=carriageMapper.getTicketLeft(nextinfo);
            nextinfo.setFirstClass(ticket[0]);
            nextinfo.setSecondClass(ticket[1]);
            nextinfo.setRW(ticket[2]);
            nextinfo.setYW(ticket[3]);
            if(ticket[0]>0||ticket[1]>0||ticket[2]>0||ticket[3]>0)
                return nextinfo;
        }
        return  null;
    }


    public List<String> getCity(){
        List<String> cites=cityStationMapper.getCity();
        return cites;
    }

    public List<Station> getTrainStationInfo(String traincode){
        return stationMapper.getTrainStationInfo(traincode);
    }
}
