package com.yance.text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yance.notice.INoticeSendComponent;
import com.yance.pojos.PromethuesNotice;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class NoticeTextResolverProvider {

	final List<NoticeTextResolver> noticeTextResolvers = Collections.synchronizedList(new LinkedList<>());

	final NoticeTextResolver defaultNoticeTextResolver = new DefaultResolver();

	private final static Log logger = LogFactory.getLog(NoticeTextResolverProvider.class);

	public NoticeTextResolverProvider(List<NoticeTextResolver> list) {
		this.noticeTextResolvers.addAll(list);
	}

	public void add(NoticeTextResolver noticeTextResolver) {
		noticeTextResolvers.add(noticeTextResolver);
	}

	public NoticeTextResolver get(Class<? extends PromethuesNotice> clazz, INoticeSendComponent noticeSendComponent) {
		Optional<NoticeTextResolver> optional = noticeTextResolvers.stream()
				.filter(x -> x.support(clazz, noticeSendComponent)).findAny();
		if (optional.isEmpty()) {
			logger.warn("no resolver is found , use default resolver");
			return defaultNoticeTextResolver;
		}
		return optional.get();
	}
}
