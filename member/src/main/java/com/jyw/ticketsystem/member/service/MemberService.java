
package com.jyw.ticketsystem.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.jyw.ticketsystem.common.exception.BusinessException;
import com.jyw.ticketsystem.common.exception.BusinessExceptionEnum;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.member.domain.Member;
import com.jyw.ticketsystem.member.domain.MemberExample;
import com.jyw.ticketsystem.member.mapper.MemberMapper;
import com.jyw.ticketsystem.member.req.MemberRegisterReq;
import com.jyw.ticketsystem.member.req.MemberSendCodeReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private static final Logger LOG= LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;
    public int count(){
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(MemberRegisterReq req) {
        String mobile= req.getMobile();
        MemberExample memberExample = new MemberExample();
        //构造while条件
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        //带验证码的注册用这种方式；接口可以注册也可以登录qwq
        if(CollUtil.isNotEmpty(list)){
//            return list.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        //如果没有注册过手机号，则插入一条记录
        if (CollUtil.isEmpty(list)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }
        else{
            LOG.info("手机号存在，不插入记录");
        }
        //生成验证码
        String code = RandomUtil.randomString(4);
        LOG.info("发送短信验证码：{}",code);
    }
}
