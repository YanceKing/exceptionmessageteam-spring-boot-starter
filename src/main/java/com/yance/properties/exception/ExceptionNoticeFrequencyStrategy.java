package com.yance.properties.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import com.yance.properties.frequency.NoticeFrequencyStrategy;

@ConfigurationProperties(prefix = "prometheus.exceptionnotice.strategy")
public class ExceptionNoticeFrequencyStrategy extends NoticeFrequencyStrategy {

}
