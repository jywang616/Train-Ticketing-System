
package com.jyw.ticketsystem.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jyw.ticketsystem.common.exception.BusinessException;
import com.jyw.ticketsystem.common.exception.BusinessExceptionEnum;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.member.domain.Member;
import com.jyw.ticketsystem.member.domain.MemberExample;
import com.jyw.ticketsystem.member.mapper.MemberMapper;
import com.jyw.ticketsystem.member.req.MemberLoginReq;
import com.jyw.ticketsystem.member.req.MemberRegisterReq;
import com.jyw.ticketsystem.member.req.MemberSendCodeReq;
import com.jyw.ticketsystem.member.resp.MemberLoginResp;
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
        Member memberDB = selectByMobile(mobile);
        //带验证码的注册用这种方式；接口可以注册也可以登录qwq
        if(ObjectUtil.isNotNull(memberDB)){
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
        Member memberDB = selectByMobile(mobile);
        //如果没有注册过手机号，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
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
        //String code = RandomUtil.randomString(4);
        String code="2333";
        LOG.info("发送短信验证码：{}",code);
    }
    public MemberLoginResp login(MemberLoginReq req) {
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = selectByMobile(mobile);
        //如果手机号没有被注册过，抛出异常
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }
        //校验短信验证码
        if(!"2333".equals(code)){
            LOG.info("验证码输入错误");
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }
       /* MemberLoginResp memberLoginResp =new MemberLoginResp();
        memberLoginResp.setId(memberDB.getId());
        memberLoginResp.setMobile(mobile);*/
        return BeanUtil.copyProperties(memberDB,MemberLoginResp.class);
    }

    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        else{
            return list.get(0);
        }
    }
}
