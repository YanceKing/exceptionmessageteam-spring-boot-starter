package com.yance.notice;

import com.yance.config.notice.NoticeSendComponentRegister;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultNoticCompoenntFactory implements NoticeComponentFactory {

	private final Map<String, List<INoticeSendComponent>> map = new ConcurrentHashMap<>();

	public DefaultNoticCompoenntFactory(NoticeSendComponentRegister componentRegister) {
		this.map.putAll(componentRegister.getRegistComponent());
	}

	@Override
	public List<INoticeSendComponent> get(String blameFor) {
		return map.get(blameFor);
	}

}
