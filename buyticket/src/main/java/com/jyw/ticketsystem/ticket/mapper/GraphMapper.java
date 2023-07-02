package com.jyw.ticketsystem.ticket.mapper;

import com.jyw.ticketsystem.ticket.domain.Graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//增删改查
public interface GraphMapper {


    public List<Graph>getGraph(String start_date);
    //可能会出错

    public default Map<String, Map<String,Integer>>getGraphs(String start_date){
        Map<String, Map<String,Integer>>result=new HashMap<>();

        List<Graph>graphs=getGraph(start_date);
        for(Graph graph:graphs){
            if(result.containsKey(graph.getStart_city())){
                Map<String,Integer> temp=result.get(graph.getStart_city());
                temp.put(graph.getEnd_city(),graph.getDuration());
            }else{
                Map<String,Integer>t=new HashMap<>();
                t.put(graph.getEnd_city(),graph.getDuration());
                result.put(graph.getStart_city(),t);
            }
        }
        return result;
    }
    public void saveGraphin(Graph graph);

    public default void saveGraph(Map<String,Map<String,Integer>>graphs,String start_date){
        for(String start_city:graphs.keySet()){
            Map<String,Integer>map=graphs.get(start_city);
            for (String end_city:map.keySet()){
                Graph graph=new Graph(start_city,end_city, map.get(end_city),start_date);
                saveGraphin(graph);
            }
        }
    }

}
