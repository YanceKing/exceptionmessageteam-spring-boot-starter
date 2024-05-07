package com.yance.message;

import com.yance.content.ExceptionNotice;
import com.yance.httpclient.SimpleHttpClient;
import com.yance.pojos.dingding.DingDingNotice;
import com.yance.pojos.dingding.DingDingResult;
import com.yance.properties.DingDingExceptionNoticeProperty;
import com.yance.properties.ExceptionNoticeProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Map;

/**
 * 异常通知 DingDingNoticeSendComponent
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public class DingDingNoticeSendComponent implements INoticeSendComponent {

	private SimpleHttpClient simpleHttpClient;

	private ExceptionNoticeProperty exceptionNoticeProperty;

	private Map<String, DingDingExceptionNoticeProperty> map;

	private final Log logger = LogFactory.getLog(getClass());

	public DingDingNoticeSendComponent(SimpleHttpClient simpleHttpClient,
			ExceptionNoticeProperty exceptionNoticeProperty, Map<String, DingDingExceptionNoticeProperty> map) {
		this.simpleHttpClient = simpleHttpClient;
		this.exceptionNoticeProperty = exceptionNoticeProperty;
		this.map = map;
	}

	/**
	 * @return the simpleHttpClient
	 */
	public SimpleHttpClient getSimpleHttpClient() {
		return simpleHttpClient;
	}

	/**
	 * @return the exceptionNoticeProperty
	 */
	public ExceptionNoticeProperty getExceptionNoticeProperty() {
		return exceptionNoticeProperty;
	}

	/**
	 * @param simpleHttpClient the simpleHttpClient to set
	 */
	public void setSimpleHttpClient(SimpleHttpClient simpleHttpClient) {
		this.simpleHttpClient = simpleHttpClient;
	}

	/**
	 * @param exceptionNoticeProperty the exceptionNoticeProperty to set
	 */
	public void setExceptionNoticeProperty(ExceptionNoticeProperty exceptionNoticeProperty) {
		this.exceptionNoticeProperty = exceptionNoticeProperty;
	}

	/**
	 * @return the map
	 */
	public Map<String, DingDingExceptionNoticeProperty> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, DingDingExceptionNoticeProperty> map) {
		this.map = map;
	}

	@Override
	public void send(String blamedFor, ExceptionNotice exceptionNotice) {
		DingDingExceptionNoticeProperty dingDingExceptionNoticeProperty = map.get(blamedFor);
		if (dingDingExceptionNoticeProperty != null) {
			DingDingNotice dingDingNotice = new DingDingNotice(exceptionNotice.createText(),
					dingDingExceptionNoticeProperty.getPhoneNum());
			DingDingResult result = simpleHttpClient.post(dingDingExceptionNoticeProperty.getWebHook(), dingDingNotice,
					DingDingResult.class);
			logger.debug(result);
		} else
			logger.error("无法进行钉钉通知，不存在背锅侠");
	}

	@Override
	public Collection<String> getAllBuddies() {
		return map.keySet();
	}

}
