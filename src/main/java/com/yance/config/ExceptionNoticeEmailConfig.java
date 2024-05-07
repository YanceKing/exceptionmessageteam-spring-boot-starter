package com.yance.config;

import com.yance.exceptionhandle.ExceptionHandler;
import com.yance.message.EmailNoticeSendComponent;
import com.yance.properties.EmailExceptionNoticeProperty;
import com.yance.properties.ExceptionNoticeProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 异常通知邮件配置
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
@Configuration
@AutoConfigureAfter({ MailSenderAutoConfiguration.class, ExceptionNoticeConfig.class })
@ConditionalOnBean({ MailSender.class, MailProperties.class })
public class ExceptionNoticeEmailConfig {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private MailProperties mailProperties;
	@Autowired
	private ExceptionHandler exceptionHandler;
	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	@PostConstruct
	public void emailNoticeSendComponent() {
		Map<String, EmailExceptionNoticeProperty> emails = exceptionNoticeProperty.getEmail();
		if (emails != null && emails.size() > 0) {
			EmailNoticeSendComponent component = new EmailNoticeSendComponent(mailSender, mailProperties, emails);
			exceptionHandler.registerNoticeSendComponent(component);
		}
	}
}
