package com.yance.codef;
import com.yance.codef.enums.ServerState;

public class ProjectState {

	private String appName;

	private String serviceName;

	private String serviceInstance;

	private String tenantId;

	private boolean success;

	private ServerState state;

	private String failReason;

	private String deployProcessUid;

	/**
	 * 状态成功
	 * 
	 * @param appName
	 * @param serviceName
	 * @param serviceInstance
	 * @param tenantId
	 * @param state
	 */
	public ProjectState(String appName, String serviceName, String serviceInstance, String tenantId, ServerState state,
			String processUid) {
		success = true;
		this.appName = appName;
		this.serviceName = serviceName;
		this.serviceInstance = serviceInstance;
		this.tenantId = tenantId;
		this.state = state;
		this.deployProcessUid = processUid;
	}

	/**
	 * 状态失败
	 * 
	 * @param appName
	 * @param serviceName
	 * @param serviceInstance
	 * @param tenantId
	 * @param state
	 * @param failReason
	 */
	public ProjectState(String appName, String serviceName, String serviceInstance, String tenantId, ServerState state,
			String failReason, String deployProcessUid) {
		success = false;
		this.appName = appName;
		this.serviceName = serviceName;
		this.serviceInstance = serviceInstance;
		this.tenantId = tenantId;
		this.state = state;
		this.failReason = failReason;
		this.deployProcessUid = deployProcessUid;
	}

	public ProjectState() {
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the serviceInstance
	 */
	public String getServiceInstance() {
		return serviceInstance;
	}

	/**
	 * @param serviceInstance the serviceInstance to set
	 */
	public void setServiceInstance(String serviceInstance) {
		this.serviceInstance = serviceInstance;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the state
	 */
	public ServerState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ServerState state) {
		this.state = state;
	}

	/**
	 * @return the failReason
	 */
	public String getFailReason() {
		return failReason;
	}

	/**
	 * @param failReason the failReason to set
	 */
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	/**
	 * @return the deployProcessUid
	 */
	public String getDeployProcessUid() {
		return deployProcessUid;
	}

	/**
	 * @param deployProcessUid the deployProcessUid to set
	 */
	public void setDeployProcessUid(String deployProcessUid) {
		this.deployProcessUid = deployProcessUid;
	}

	@Override
	public String toString() {
		return "ProjectState [appName=" + appName + ", serviceName=" + serviceName + ", serviceInstance="
				+ serviceInstance + ", tenantId=" + tenantId + ", success=" + success + ", state=" + state
				+ ", failReason=" + failReason + ", deployProcessUid=" + deployProcessUid + "]";
	}

}
