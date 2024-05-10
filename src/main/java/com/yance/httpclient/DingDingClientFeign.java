package com.yance.httpclient;

import com.yance.pojos.dingding.DingDingNotice;
import com.yance.pojos.dingding.DingDingResult;
import feign.*;

import java.util.Map;

public interface DingDingClientFeign {

	@RequestLine("POST /send?access_token={accessToken}")
	@Headers("Content-Type: application/json; charset=utf-8")
	@Body("{body}")
	DingDingResult post(@Param("accessToken") String accessToken, DingDingNotice body,
						@QueryMap Map<String, Object> map);
}
