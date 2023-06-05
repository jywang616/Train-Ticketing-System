package com.jyw.ticketsystem.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.common.context.LoginMemberContext;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.member.domain.${Domain};
import com.jyw.ticketsystem.member.domain.${Domain}Example;
import com.jyw.ticketsystem.member.mapper.${Domain}Mapper;
import com.jyw.ticketsystem.member.req.${Domain}QueryReq;
import com.jyw.ticketsystem.member.req.${Domain}SaveReq;
import com.jyw.ticketsystem.member.resp.${Domain}QueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${Domain}Service {

    @Resource
    private ${Domain}Mapper ${domain}Mapper;
    public void save(${Domain}SaveReq req){
        DateTime now=DateTime.now();
        ${Domain} ${domain}=BeanUtil.copyProperties(req,${Domain}.class);
        if(ObjectUtil.isNull(${domain}.getId())) {
            ${domain}.setMemberId(LoginMemberContext.getId());
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        }
        else{
            ${domain}.setCreateTime(now);
            ${domain}Mapper.updateByPrimaryKey(${domain});
        }
    }
    public PageResp<${Domain}QueryResp> queryList(${Domain}QueryReq req){
        ${Domain}Example ${domain}Example=new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria=${domain}Example.createCriteria();
        if(ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<${Domain}> ${domain}List=${domain}Mapper.selectByExample((${domain}Example));

        PageInfo<${Domain}> pageInfo= new PageInfo<>(${domain}List);

        List<${Domain}QueryResp> list= BeanUtil.copyToList(${domain}List,${Domain}QueryResp.class);
        PageResp<${Domain}QueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
