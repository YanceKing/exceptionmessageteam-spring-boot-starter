package com.yance.notice;

import java.util.List;

public interface NoticeComponentFactory {

	public List<INoticeSendComponent> get(String blameFor);
}
