package com.jyw.ticketsystem.business.feign;

import org.springframework.cloud.openfeign.FeignClient;

// @FeignClient("member")
@FeignClient(name = "member", url = "http://127.0.0.1:8001")
public interface MemberFeign {

}
