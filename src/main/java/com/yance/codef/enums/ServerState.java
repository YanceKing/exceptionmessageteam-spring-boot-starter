package com.yance.codef.enums;

import org.springframework.boot.actuate.health.Health;

import java.util.Arrays;

public enum ServerState {

	UP("UP"), DOWN("DOWN"), OUT_OF_SERVICE("OUT_OF_SERVICE"), UNKNOW("UNKNOWN");

	private final String value;

	/**
	 * @param value
	 */
	private ServerState(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	public static ServerState state(Health health) {
		return Arrays.stream(ServerState.values()).filter(x -> health.getStatus().getCode().equals(x.value)).findAny()
				.orElseGet(null);
	}

}
