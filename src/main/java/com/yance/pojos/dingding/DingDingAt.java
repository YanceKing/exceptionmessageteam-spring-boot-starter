package com.yance.pojos.dingding;

/**
 * 异常通知钉钉 @ 所有人 @ 个人 等
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public class DingDingAt {

	private String[] atMobiles;

	private boolean isAtAll = false;

	public DingDingAt(String... atMobiles) {
		this.atMobiles = atMobiles;
	}

	/**
	 * @return the atMobiles
	 */
	public String[] getAtMobiles() {
		return atMobiles;
	}

	/**
	 * @param atMobiles the atMobiles to set
	 */
	public void setAtMobiles(String[] atMobiles) {
		this.atMobiles = atMobiles;
	}

	/**
	 * @return the isAtAll
	 */
	public boolean isAtAll() {
		return isAtAll;
	}

	/**
	 * @param isAtAll the isAtAll to set
	 */
	public void setAtAll(boolean isAtAll) {
		this.isAtAll = isAtAll;
	}

}
