package com.yance.config.notice;

import com.yance.text.NoticeTextResolverProvider;
import org.springframework.core.Ordered;

public interface NoticeTextResolverCustomer extends Ordered{

	public void custom(NoticeTextResolverProvider resolverProvider);
}
