package com.yance.common.abstracts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.yance.exceptionhandle.event.ExceptionNoticeEvent;
import com.yance.exceptionhandle.interfaces.NoticeStatisticsRepository;
import com.yance.notice.INoticeSendComponent;
import com.yance.notice.NoticeComponentFactory;
import com.yance.pojos.NoticeStatistics;
import com.yance.pojos.PromethuesNotice;
import com.yance.properties.frequency.NoticeFrequencyStrategy;
import com.yance.text.NoticeTextResolverProvider;
import org.springframework.context.ApplicationListener;

/**
 * @author 徐晓东
 *
 */
public abstract class AbstractNoticeSendListener implements ApplicationListener<ExceptionNoticeEvent> {

	private final NoticeFrequencyStrategy noticeFrequencyStrategy;

	private final NoticeStatisticsRepository exceptionNoticeStatisticsRepository;

	private final NoticeTextResolverProvider resolverProvider;

	private final NoticeComponentFactory noticeComponentFactory;

	public AbstractNoticeSendListener(NoticeFrequencyStrategy noticeFrequencyStrategy,
			NoticeStatisticsRepository exceptionNoticeStatisticsRepository, NoticeTextResolverProvider resolverProvider,
			NoticeComponentFactory noticeComponentFactory) {
		this.noticeFrequencyStrategy = noticeFrequencyStrategy;
		this.exceptionNoticeStatisticsRepository = exceptionNoticeStatisticsRepository;
		this.resolverProvider = resolverProvider;
		this.noticeComponentFactory = noticeComponentFactory;
	}

	public void send(String who, PromethuesNotice notice) {
		List<INoticeSendComponent> noticeSendComponents = noticeComponentFactory.get(who);
		NoticeStatistics statistics = exceptionNoticeStatisticsRepository.increaseOne(notice);
		if (stratergyCheck(statistics, noticeFrequencyStrategy)) {
			notice.setShowCount(statistics.getShowCount().longValue());
			notice.setCreateTime(LocalDateTime.now());
			noticeSendComponents.forEach(x -> {
				x.send(notice, resolverProvider.get(notice.getClass(), x));
			});
			exceptionNoticeStatisticsRepository.increaseShowOne(statistics);
		}
	}

	/**
	 * @param exceptionStatistics
	 * @param noticeFrequencyStrategy
	 * @return
	 */
	protected boolean stratergyCheck(NoticeStatistics exceptionStatistics,
									 NoticeFrequencyStrategy noticeFrequencyStrategy) {
		if (exceptionStatistics.isFirstCreated()) {
			exceptionStatistics.setFirstCreated(false);
			return true;
		}
		boolean flag = false;
		switch (noticeFrequencyStrategy.getFrequencyType()) {
		case TIMEOUT -> {
			Duration dur = Duration.between(exceptionStatistics.getNoticeTime(), LocalDateTime.now());
			flag = noticeFrequencyStrategy.getNoticeTimeInterval().compareTo(dur) < 0;
		}
		case SHOWCOUNT -> {
			flag = exceptionStatistics.getShowCount().longValue() - exceptionStatistics.getLastNoticedCount()
					.longValue() > noticeFrequencyStrategy.getNoticeShowCount().longValue();
		}
		default -> {
		}
		}
		return flag;
	}
}
