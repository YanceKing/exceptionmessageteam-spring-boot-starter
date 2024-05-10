package com.yance.httpclient;


import com.yance.pojos.dingding.DingDingNotice;
import com.yance.pojos.dingding.DingDingResult;
import com.yance.properties.notice.DingDingNoticeProperty;

@FunctionalInterface
public interface DingdingHttpClient {

	DingDingResult doSend(DingDingNotice notice, DingDingNoticeProperty dingDingNoticeProperty);

}
