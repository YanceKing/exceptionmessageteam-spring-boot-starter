package com.yance.config.notice;

import com.yance.config.conditions.PrometheusEnabledCondition;
import com.yance.notice.EmailNoticeSendComponent;
import com.yance.notice.INoticeSendComponent;
import com.yance.properties.notice.PrometheusNoticeProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.mail.MailSender;

@Configuration
@ConditionalOnBean({ MailSender.class, MailProperties.class })
@AutoConfigureAfter({ MailSenderAutoConfiguration.class })
@Conditional(PrometheusEnabledCondition.class)
public class EmailNoticeSendAutoConfig implements NoticeSendComponentCustomer {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private MailProperties mailProperties;

	@Autowired
	private PrometheusNoticeProperties prometheusNoticeProperties;

	private static final Log logger = LogFactory.getLog(EmailNoticeSendAutoConfig.class);

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public void custom(NoticeSendComponentRegister register) {
		logger.debug("邮件通知组件注册");
		if (prometheusNoticeProperties.getEmail() != null)
			prometheusNoticeProperties.getEmail().forEach((x, y) -> {
				INoticeSendComponent component = new EmailNoticeSendComponent(mailSender, mailProperties, y);
				register.add(x, component);
			});
	}

}
