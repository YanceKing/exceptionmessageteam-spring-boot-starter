package com.yance.config.notice;

import com.yance.notice.INoticeSendComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NoticeSendComponentRegister {

	private final Map<String, List<INoticeSendComponent>> registComponent = Collections
			.synchronizedMap(new ConcurrentHashMap<>());

	public synchronized void add(String blameFor, INoticeSendComponent noticeSendComponent) {
		List<INoticeSendComponent> list = registComponent.getOrDefault(blameFor, new ArrayList<>());
		if (list.stream().filter(x -> x.name().equals(noticeSendComponent.name())).findAny().isEmpty()) {
			list.add(noticeSendComponent);
		}
		registComponent.put(blameFor, list);
	}

	/**
	 * @return the registComponent
	 */
	public Map<String, List<INoticeSendComponent>> getRegistComponent() {
		return registComponent;
	}

}
