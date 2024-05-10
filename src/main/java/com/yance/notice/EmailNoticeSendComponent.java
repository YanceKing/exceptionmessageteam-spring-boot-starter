package com.yance.notice;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import com.yance.pojos.PromethuesNotice;
import com.yance.properties.notice.EmailNoticeProperty;
import com.yance.text.NoticeTextResolver;

import java.util.regex.Pattern;

public class EmailNoticeSendComponent implements INoticeSendComponent, InitializingBean {

//	private final Log logger = LogFactory.getLog(getClass());

	private final MailSender mailSender;

	private final MailProperties mailProperties;

	private final EmailNoticeProperty emailNoticeProperty;

	private final static String NAME = "email";

	public EmailNoticeSendComponent(MailSender mailSender, MailProperties mailProperties,
			EmailNoticeProperty emailNoticeProperty) {
		this.mailSender = mailSender;
		this.mailProperties = mailProperties;
		this.emailNoticeProperty = emailNoticeProperty;
	}

	@Override
	public void send(PromethuesNotice notice, NoticeTextResolver noticeTextResolver) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String fromEmail = mailProperties.getUsername();
		mailMessage.setFrom(fromEmail);
		mailMessage.setTo(emailNoticeProperty.getTo());
		String[] cc = emailNoticeProperty.getCc();
		if (cc != null && cc.length > 0)
			mailMessage.setCc(cc);
		String[] bcc = emailNoticeProperty.getBcc();
		if (bcc != null && bcc.length > 0)
			mailMessage.setBcc(bcc);
		mailMessage.setText(noticeTextResolver.resolve(notice));
		mailMessage
				.setSubject(String.format("一个来自%s的提醒（%s）", notice.getTitle(), notice.getProjectEnviroment().getName()));
		mailSender.send(mailMessage);
	}

	private boolean isEmail(String email) {
		if (email != null)
			return Pattern.matches("^[A-Za-z0-9_\\-]+@[a-zA-Z0-9_\\-]+(\\.[a-zA-Z]{2,4})+$", email);
		return false;
	}

	private void checkAllEmails(EmailNoticeProperty emailExceptionNoticeProperty) {
		String fromEmail = mailProperties.getUsername();
		if (fromEmail != null && !isEmail(fromEmail))
			throw new IllegalArgumentException("发件人邮箱错误");
		String[] toEmail = emailExceptionNoticeProperty.getTo();
		if (toEmail != null) {
			for (String email : toEmail) {
				if (!isEmail(email))
					throw new IllegalArgumentException("收件人邮箱错误");
			}
		}
		String[] ccEmail = emailExceptionNoticeProperty.getCc();
		if (ccEmail != null) {
			for (String email : ccEmail) {
				if (!isEmail(email))
					throw new IllegalArgumentException("抄送人邮箱错误");
			}
		}
		String[] bccEmail = emailExceptionNoticeProperty.getBcc();
		if (bccEmail != null) {
			for (String email : bccEmail) {
				if (!isEmail(email))
					throw new IllegalArgumentException("秘密抄送人邮箱错误");
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		checkAllEmails(emailNoticeProperty);
	}

	@Override
	public String name() {
		return NAME;
	}

}
