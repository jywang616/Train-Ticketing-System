
package com.jyw.ticketsystem.member.service;

import cn.hutool.core.collection.CollUtil;
import com.jyw.ticketsystem.common.exception.BusinessException;
import com.jyw.ticketsystem.common.exception.BusinessExceptionEnum;
import com.jyw.ticketsystem.member.domain.Member;
import com.jyw.ticketsystem.member.domain.MemberExample;
import com.jyw.ticketsystem.member.mapper.MemberMapper;
import com.jyw.ticketsystem.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

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
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
}
