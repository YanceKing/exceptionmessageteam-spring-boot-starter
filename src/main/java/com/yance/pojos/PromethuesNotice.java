package com.yance.pojos;

import com.yance.properties.enums.ProjectEnviroment;

import java.time.LocalDateTime;

public abstract class PromethuesNotice implements UniqueMessage {

	/**
	 * 通知标题
	 */
	protected String title;

	/**
	 * 工程环境
	 */
	protected ProjectEnviroment projectEnviroment;

	/**
	 * 通知时间
	 */
	protected LocalDateTime createTime = LocalDateTime.now();

	/**
	 * 出现次数
	 */
	protected Long showCount = 1L;

	/**
	 * @param title
	 * @param projectEnviroment
	 */
	public PromethuesNotice(String title, ProjectEnviroment projectEnviroment) {
		this.title = title;
		this.projectEnviroment = projectEnviroment;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the projectEnviroment
	 */
	public ProjectEnviroment getProjectEnviroment() {
		return projectEnviroment;
	}

	/**
	 * @param projectEnviroment the projectEnviroment to set
	 */
	public void setProjectEnviroment(ProjectEnviroment projectEnviroment) {
		this.projectEnviroment = projectEnviroment;
	}

	/**
	 * @return the createTime
	 */
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
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
		return "PromethuesNotice [title=" + title + ", projectEnviroment=" + projectEnviroment + ", createTime="
				+ createTime + ", showCount=" + showCount + "]";
	}

}
