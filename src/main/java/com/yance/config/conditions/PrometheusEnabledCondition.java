package com.yance.config.conditions;

import org.springframework.core.annotation.Order;

/**
 *
 * @ClassName: PrometheusEnabledCondition
 * @Description: Prometheus是否开启
 * @author yance
 * @date 2019年7月1日 下午3:03:01
 *
 */
@Order(0)
public class PrometheusEnabledCondition extends PropertiesEnabledCondition {

	public PrometheusEnabledCondition() {
		super("prometheus.enabled", true);
	}

}
