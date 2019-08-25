package com.yance.message;

import com.yance.content.ExceptionNotice;
import com.yance.properties.EmailExceptionNoticeProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class EmailNoticeSendComponent implements INoticeSendComponent {

	private final Log logger = LogFactory.getLog(getClass());

	private MailSender mailSender;

	private MailProperties mailProperties;

	private Map<String, EmailExceptionNoticeProperty> map;

	public EmailNoticeSendComponent(MailSender mailSender, MailProperties mailProperties,
			Map<String, EmailExceptionNoticeProperty> map) {
		this.mailSender = mailSender;
		this.mailProperties = mailProperties;
		this.map = map;
		checkAllEmails();

	}

	public EmailNoticeSendComponent() {
	}

	@Override
	public void send(String blamedFor, ExceptionNotice exceptionNotice) {
		EmailExceptionNoticeProperty emailExceptionNoticeProperty = map.get(blamedFor);
		if (emailExceptionNoticeProperty != null) {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			String fromEmail = emailExceptionNoticeProperty.getFrom();
			fromEmail = fromEmail == null ? mailProperties.getUsername() : fromEmail;
			mailMessage.setFrom(fromEmail);
			mailMessage.setTo(emailExceptionNoticeProperty.getTo());
			String[] cc = emailExceptionNoticeProperty.getCc();
			if (cc != null && cc.length > 0)
				mailMessage.setCc(cc);
			String[] bcc = emailExceptionNoticeProperty.getBcc();
			if (bcc != null && bcc.length > 0)
				mailMessage.setBcc(bcc);
			mailMessage.setText(exceptionNotice.createText());
			mailMessage.setSubject(String.format("来自%s的异常提醒", exceptionNotice.getProject()));
			mailSender.send(mailMessage);
		} else
			logger.error("无法发送异常通知，不存在的背锅侠");
	}

	private boolean isEmail(String email) {
		if (email != null)
			return Pattern.matches("^[A-Za-z0-9_\\-]+@[a-zA-Z0-9_\\-]+(\\.[a-zA-Z]{2,4})+$", email);
		return false;
	}

	private void checkAllEmails() {
		map.forEach((x, y) -> checkAllEmails(y));
	}

	private void checkAllEmails(EmailExceptionNoticeProperty emailExceptionNoticeProperty) {
		String fromEmail = emailExceptionNoticeProperty.getFrom();
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

	/**
	 * @return the mailSender
	 */
	public MailSender getMailSender() {
		return mailSender;
	}

	/**
	 * @param mailSender the mailSender to set
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * @return the mailProperties
	 */
	public MailProperties getMailProperties() {
		return mailProperties;
	}

	/**
	 * @param mailProperties the mailProperties to set
	 */
	public void setMailProperties(MailProperties mailProperties) {
		this.mailProperties = mailProperties;
	}

	/**
	 * @return the map
	 */
	public Map<String, EmailExceptionNoticeProperty> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, EmailExceptionNoticeProperty> map) {
		this.map = map;
	}

	@Override
	public Collection<String> getAllBuddies() {
		return map.keySet();
	}

}
