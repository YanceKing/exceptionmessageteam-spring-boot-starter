package com.yance.exceptionhandle.components;


import com.yance.exceptionhandle.interfaces.NoticeStatisticsRepository;
import com.yance.pojos.NoticeStatistics;
import com.yance.pojos.PromethuesNotice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemeryExceptionStatisticsRepository implements NoticeStatisticsRepository {

	private final Map<String, NoticeStatistics> map = Collections.synchronizedMap(new HashMap<>());

	@Override
	public NoticeStatistics increaseOne(PromethuesNotice exceptionNotice) {
		NoticeStatistics exceptionStatistics = map.getOrDefault(exceptionNotice.getUid(),
				new NoticeStatistics(exceptionNotice.getUid()));
		if (exceptionStatistics.isFirstCreated()) {
			synchronized (exceptionStatistics) {
				map.merge(exceptionStatistics.getUid(), exceptionStatistics, (x, y) -> {
					if (x == null) {
						return y;
					} else {
						x.setFirstCreated(false);
						return x;
					}
				});
			}
		}
		exceptionStatistics.plusOne();
		return exceptionStatistics;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public void increaseShowOne(NoticeStatistics exceptionStatistics) {
		exceptionStatistics.refreshShow();
	}

}
