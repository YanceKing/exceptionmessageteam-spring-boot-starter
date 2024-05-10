package com.yance.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import com.yance.properties.enums.ProjectEnviroment;

@ConfigurationProperties(prefix = "prometheus")
public class PrometheusProperties {

	/**
	 * 是否开启异常通知
	 */
	private boolean enabled = false;

	/**
	 * 异常工程名
	 */
	@Value("${prometheus.project-name:${spring.application.name:project}:application}")
	private String projectName;

	/**
	 * 工程的发布环境，主要分为5个：开发环境、测试环境、预发环境、正式环境与回滚环境
	 */
	private ProjectEnviroment projectEnviroment = ProjectEnviroment.DEVELOP;

	/**
	 * 默认的通知人
	 */
	private String defaultName;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ProjectEnviroment getProjectEnviroment() {
		return projectEnviroment;
	}

	public void setProjectEnviroment(ProjectEnviroment projectEnviroment) {
		this.projectEnviroment = projectEnviroment;
	}

	/**
	 * @return the defaultName
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * @param defaultName the defaultName to set
	 */
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

}
