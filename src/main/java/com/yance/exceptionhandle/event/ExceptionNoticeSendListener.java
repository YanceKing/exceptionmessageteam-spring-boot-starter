package com.yance.exceptionhandle.event;

import com.yance.common.abstracts.AbstractNoticeSendListener;
import com.yance.exceptionhandle.interfaces.NoticeStatisticsRepository;
import com.yance.notice.NoticeComponentFactory;
import com.yance.properties.frequency.NoticeFrequencyStrategy;
import com.yance.text.NoticeTextResolverProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExceptionNoticeSendListener extends AbstractNoticeSendListener {

    private final static Log logger = LogFactory.getLog(ExceptionNoticeSendListener.class);

    /**
     * @param noticeFrequencyStrategy
     * @param exceptionNoticeStatisticsRepository
     * @param resolverProvider
     * @param noticeComponentFactory
     * @param resolverName
     */
    public ExceptionNoticeSendListener(NoticeFrequencyStrategy noticeFrequencyStrategy,
                                       NoticeStatisticsRepository exceptionNoticeStatisticsRepository, NoticeTextResolverProvider resolverProvider,
                                       NoticeComponentFactory noticeComponentFactory, String resolverName) {
        super(noticeFrequencyStrategy, exceptionNoticeStatisticsRepository, resolverProvider, noticeComponentFactory);
    }

    @Override
    public void onApplicationEvent(ExceptionNoticeEvent event) {
        logger.debug("消息同步发送");
        send(event.getBlameFor(), event.getExceptionNotice());
    }

}
