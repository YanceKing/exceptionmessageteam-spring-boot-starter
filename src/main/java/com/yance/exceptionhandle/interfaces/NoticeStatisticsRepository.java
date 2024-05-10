package com.yance.exceptionhandle.interfaces;
import com.yance.pojos.NoticeStatistics;
import com.yance.pojos.PromethuesNotice;

public interface NoticeStatisticsRepository {

	public NoticeStatistics increaseOne(PromethuesNotice exceptionNotice);

	public void increaseShowOne(NoticeStatistics exceptionStatistics);

	public void clear();
}
