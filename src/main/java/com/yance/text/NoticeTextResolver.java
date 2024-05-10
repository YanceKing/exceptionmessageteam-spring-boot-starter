package com.yance.text;

import com.yance.notice.INoticeSendComponent;
import com.yance.pojos.PromethuesNotice;

public interface NoticeTextResolver {

	/**
	 * 将通知信息进行格式化并转换为字符串
	 * 
	 * @param notice
	 * @return
	 */
	public String resolve(PromethuesNotice notice);

	/**
	 * 通知信息结构是否支持此发送组件发送
	 * 
	 * @param clazz
	 * @param sendComponent
	 * @return
	 */
	public boolean support(Class<? extends PromethuesNotice> clazz, INoticeSendComponent sendComponent);
}
