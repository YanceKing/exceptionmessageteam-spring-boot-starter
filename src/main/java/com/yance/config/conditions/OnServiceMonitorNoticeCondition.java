package com.yance.config.conditions;

import org.springframework.core.annotation.Order;

/**
 * @author yance
 * @version 1.0
 * @date 2020/12/24
 * @description 异常监控
 */
@Order(10)
public class OnServiceMonitorNoticeCondition extends PropertiesEnabledCondition {

    public OnServiceMonitorNoticeCondition() {
        super("prometheus.service-monitor.enabled", false);
    }

}
