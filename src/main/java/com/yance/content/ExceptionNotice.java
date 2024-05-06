package com.yance.content;

import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ExceptionNotice {

	/**
	 * 工程名
	 */
	protected String project;

	/**
	 * 异常的标识码
	 */
	protected String uid;

	/**
	 * 方法名
	 */
	protected String methodName;

	/**
	 * 方法参数信息
	 */
	protected List<Object> parames;

	/**
	 * 类路径
	 */
	protected String classPath;

	/**
	 * 异常信息
	 */
	protected String exceptionMessage;

	/**
	 * 异常追踪信息
	 */
	protected List<String> traceInfo = new ArrayList<>();

	/**
	 * 最后一次出现的时间
	 */
	protected LocalDateTime latestShowTime = LocalDateTime.now();

	/**
	 * 出现次数
	 */
	protected Long showCount = 1L;

	public ExceptionNotice(Throwable ex, String filterTrace, Object[] args) {
		this.exceptionMessage = gainExceptionMessage(ex);
		this.parames = args == null ? null : Arrays.stream(args).collect(toList());
		List<StackTraceElement> list = Arrays.stream(ex.getStackTrace())
				.filter(x -> filterTrace == null ? true : x.getClassName().startsWith(filterTrace))
				.filter(x -> !x.getFileName().equals("<generated>")).collect(toList());
		if (list.size() > 0) {
			this.traceInfo = list.stream().map(x -> x.toString()).collect(toList());
			this.methodName = list.get(0).getMethodName();
			this.classPath = list.get(0).getClassName();
		}
		this.uid = calUid();
	}

	public ExceptionNotice(Throwable ex, String filterTrace, Long showCount, Object[] args) {
		this.exceptionMessage = gainExceptionMessage(ex);
		this.showCount = showCount;
		this.parames = args == null ? null : Arrays.stream(args).collect(toList());
		List<StackTraceElement> list = Arrays.stream(ex.getStackTrace())
				.filter(x -> filterTrace == null ? true : x.getClassName().startsWith(filterTrace))
				.filter(x -> !x.getFileName().equals("<generated>")).collect(toList());
		if (list.size() > 0) {
			this.traceInfo = list.stream().map(x -> x.toString()).collect(toList());
			this.methodName = list.get(0).getMethodName();
			this.classPath = list.get(0).getClassName();
		}
		this.uid = calUid();
	}

	private String gainExceptionMessage(Throwable exception) {
		String em = exception.toString();
		if (exception.getCause() != null)
			em = String.format("%s\r\n\tcaused by : %s", em, gainExceptionMessage(exception.getCause()));
		return em;
	}

	private String calUid() {
		String md5 = DigestUtils.md5DigestAsHex(
				String.format("%s-%s", exceptionMessage, traceInfo.size() > 0 ? traceInfo.get(0) : "").getBytes());
		return md5;
	}

	public String createText() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("工程信息：").append(project).append("\r\n");
		stringBuilder.append("类路径：").append(classPath).append("\r\n");
		stringBuilder.append("方法名：").append(methodName).append("\r\n");
		if (parames != null && parames.size() > 0) {
			stringBuilder.append("参数信息：")
					.append(String.join(",", parames.stream().map(x -> x.toString()).collect(toList()))).append("\r\n");
		}
		stringBuilder.append("异常信息：").append(exceptionMessage).append("\r\n");
		stringBuilder.append("异常追踪：").append("\r\n").append(String.join("\r\n", traceInfo)).append("\r\n");
		stringBuilder.append("最后一次出现时间：")
				.append(latestShowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\r\n");
		stringBuilder.append("出现次数：").append(showCount).append("\r\n");
		return stringBuilder.toString();

	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the parames
	 */
	public List<Object> getParames() {
		return parames;
	}

	/**
	 * @param parames the parames to set
	 */
	public void setParames(List<Object> parames) {
		this.parames = parames;
	}

	/**
	 * @return the classPath
	 */
	public String getClassPath() {
		return classPath;
	}

	/**
	 * @param classPath the classPath to set
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * @return the traceInfo
	 */
	public List<String> getTraceInfo() {
		return traceInfo;
	}

	/**
	 * @param traceInfo the traceInfo to set
	 */
	public void setTraceInfo(List<String> traceInfo) {
		this.traceInfo = traceInfo;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the latestShowTime
	 */
	public LocalDateTime getLatestShowTime() {
		return latestShowTime;
	}

	/**
	 * @param latestShowTime the latestShowTime to set
	 */
	public void setLatestShowTime(LocalDateTime latestShowTime) {
		this.latestShowTime = latestShowTime;
	}

	/**
	 * @return the showCount
	 */
	public Long getShowCount() {
		return showCount;
	}

	/**
	 * @param showCount the showCount to set
	 */
	public void setShowCount(Long showCount) {
		this.showCount = showCount;
	}

	@Override
	public String toString() {
		return "ExceptionNotice [project=" + project + ", uid=" + uid + ", methodName=" + methodName + ", parames="
				+ parames + ", classPath=" + classPath + ", exceptionMessage=" + exceptionMessage + ", traceInfo="
				+ traceInfo + ", latestShowTime=" + latestShowTime + ", showCount=" + showCount + "]";
	}

}
