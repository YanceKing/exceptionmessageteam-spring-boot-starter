package com.yance.exceptionhandle;

import com.yance.content.ExceptionNotice;
import com.yance.content.HttpExceptionNotice;
import com.yance.content.MultiTenantExceptionNotice;
import com.yance.message.INoticeSendComponent;
import com.yance.pojos.ExceptionStatistics;
import com.yance.properties.ExceptionNoticeFrequencyStrategy;
import com.yance.properties.ExceptionNoticeProperty;
import com.yance.redis.ExceptionRedisStorageComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class ExceptionHandler {

	private ExceptionRedisStorageComponent exceptionRedisStorageComponent;

	private ExceptionNoticeProperty exceptionNoticeProperty;

	private ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy;

	private final Map<String, INoticeSendComponent> blameMap = new HashMap<>();

	private final Map<String, ExceptionStatistics> checkUid = Collections.synchronizedMap(new HashMap<>());

	private final Log logger = LogFactory.getLog(getClass());

	public ExceptionHandler(ExceptionNoticeProperty exceptionNoticeProperty,
			Collection<INoticeSendComponent> noticeSendComponents,
			ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy) {
		this.exceptionNoticeFrequencyStrategy = exceptionNoticeFrequencyStrategy;
		noticeSendComponents.forEach(x -> x.getAllBuddies().forEach(y -> blameMap.putIfAbsent(y, x)));
		this.exceptionNoticeProperty = exceptionNoticeProperty;
	}

	/**
	 * @param exceptionRedisStorageComponent the exceptionRedisStorageComponent to
	 *                                       set
	 */
	public void setExceptionRedisStorageComponent(ExceptionRedisStorageComponent exceptionRedisStorageComponent) {
		this.exceptionRedisStorageComponent = exceptionRedisStorageComponent;
	}

	public void registerNoticeSendComponent(INoticeSendComponent component) {
		component.getAllBuddies().forEach(x -> blameMap.putIfAbsent(x, component));
	}

	/**
	 * 最基础的异常通知的创建方法
	 * 
	 * @param blamedFor 谁背锅？
	 * @param exception 异常信息
	 * 
	 * @return
	 */
	public ExceptionNotice createNotice(String blamedFor, RuntimeException exception) {
		blamedFor = checkBlameFor(blamedFor);
		if (containsException(exception))
			return null;
		ExceptionNotice exceptionNotice = new ExceptionNotice(exception,
				exceptionNoticeProperty.getIncludedTracePackage(), null);
		exceptionNotice.setProject(exceptionNoticeProperty.getProjectName());
		boolean noHas = persist(exceptionNotice);
		if (noHas)
			messageSend(blamedFor, exceptionNotice);
		return exceptionNotice;

	}

	private boolean containsException(RuntimeException exception) {
		Class<? extends RuntimeException> thisEClass = exception.getClass();
		List<Class<? extends RuntimeException>> list = exceptionNoticeProperty.getExcludeExceptions();
		for (Class<? extends RuntimeException> clazz : list) {
			if (clazz.isAssignableFrom(thisEClass))
				return true;
		}
		return false;
	}

	/**
	 * 反射方式获取方法中出现的异常进行的通知
	 * 
	 * @param blamedFor 谁背锅？
	 * @param ex        异常信息
	 * @param method    方法名
	 * @param args      参数信息
	 * @return
	 */
	public ExceptionNotice createNotice(String blamedFor, RuntimeException ex, String method, Object[] args) {
		blamedFor = checkBlameFor(blamedFor);
		if (containsException(ex))
			return null;
		ExceptionNotice exceptionNotice = new ExceptionNotice(ex, exceptionNoticeProperty.getIncludedTracePackage(),
				args);
		exceptionNotice.setProject(exceptionNoticeProperty.getProjectName());
		boolean noHas = persist(exceptionNotice);
		if (noHas)
			messageSend(blamedFor, exceptionNotice);
		return exceptionNotice;

	}

	/**
	 * 创建一个http请求异常的通知
	 * 
	 * @param blamedFor
	 * @param exception
	 * @param url
	 * @param param
	 * @param requesBody
	 * @param headers
	 * @return
	 */
	public HttpExceptionNotice createHttpNotice(String blamedFor, RuntimeException exception, String url,
			Map<String, String> param, String requesBody, Map<String, String> headers) {
		blamedFor = checkBlameFor(blamedFor);
		if (containsException(exception))
			return null;
		HttpExceptionNotice exceptionNotice = new HttpExceptionNotice(exception,
				exceptionNoticeProperty.getIncludedTracePackage(), url, param, requesBody, headers);
		exceptionNotice.setProject(exceptionNoticeProperty.getProjectName());
		boolean noHas = persist(exceptionNotice);
		if (noHas)
			messageSend(blamedFor, exceptionNotice);
		return exceptionNotice;
	}

	/**
	 * 多租户中处理背锅信息
	 * 
	 * @param blamedFor
	 * @param exception
	 * @param url
	 * @param param
	 * @param requestBody
	 * @param headers
	 * @param tenantId
	 * @return
	 */
	public MultiTenantExceptionNotice createHttpNotice(String blamedFor, RuntimeException exception, String url,
			Map<String, String> param, String requestBody, Map<String, String> headers, String tenantId) {
		blamedFor = checkBlameFor(blamedFor);
		if (containsException(exception))
			return null;
		MultiTenantExceptionNotice exceptionNotice = new MultiTenantExceptionNotice(exception,
				exceptionNoticeProperty.getIncludedTracePackage(), url, param, requestBody, headers, tenantId);
		exceptionNotice.setProject(exceptionNoticeProperty.getProjectName());
		boolean noHas = persist(exceptionNotice);
		if (noHas)
			messageSend(blamedFor, exceptionNotice);
		return exceptionNotice;
	}

	private boolean persist(ExceptionNotice exceptionNotice) {
		Boolean needNotice = false;
		String uid = exceptionNotice.getUid();
		ExceptionStatistics exceptionStatistics = checkUid.get(uid);
		logger.debug(exceptionStatistics);
		if (exceptionStatistics != null) {
			Long count = exceptionStatistics.plusOne();
			if (exceptionNoticeFrequencyStrategy.getEnabled()) {
				if (stratergyCheck(exceptionStatistics, exceptionNoticeFrequencyStrategy)) {
					LocalDateTime now = LocalDateTime.now();
					exceptionNotice.setLatestShowTime(now);
					exceptionNotice.setShowCount(count);
					exceptionStatistics.setLastShowedCount(count);
					exceptionStatistics.setNoticeTime(now);
					needNotice = true;
				}
			}
		} else {
			exceptionStatistics = new ExceptionStatistics(uid);
			synchronized (exceptionStatistics) {
				checkUid.put(uid, exceptionStatistics);
				needNotice = true;
			}
		}
		if (exceptionRedisStorageComponent != null)
			exceptionRedisStorageComponent.save(exceptionNotice);
		return needNotice;
	}

	private String checkBlameFor(String blameFor) {
		blameFor = StringUtils.isEmpty(blameFor) || (!blameMap.containsKey(blameFor))
				? exceptionNoticeProperty.getDefaultNotice()
				: blameFor;
		return blameFor;
	}

	private boolean stratergyCheck(ExceptionStatistics exceptionStatistics,
			ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy) {
		switch (exceptionNoticeFrequencyStrategy.getFrequencyType()) {
		case TIMEOUT:
			Duration dur = Duration.between(exceptionStatistics.getNoticeTime(), LocalDateTime.now());
			return exceptionNoticeFrequencyStrategy.getNoticeTimeInterval().compareTo(dur) < 0;
		case SHOWCOUNT:
			return exceptionStatistics.getShowCount().longValue() - exceptionStatistics.getLastShowedCount()
					.longValue() > exceptionNoticeFrequencyStrategy.getNoticeShowCount().longValue();
		}
		return false;
	}

	private void messageSend(String blamedFor, ExceptionNotice exceptionNotice) {
		INoticeSendComponent sendComponent = blameMap.get(blamedFor);
		sendComponent.send(blamedFor, exceptionNotice);
	}

	@Scheduled(cron = "0 25 0 * * * ")
	public void resetCheck() {
		checkUid.clear();
	}
}
