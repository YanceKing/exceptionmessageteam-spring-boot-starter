package com.yance.config.conditions;

import org.springframework.core.annotation.Order;

/**
 * 异常条件检测
 *
 * @author yance
 * @version 1.0
 * @date 2019年1月17日
 * @description
 */
@Order(10)
public class OnExceptionNoticeContition extends PropertiesEnabledCondition {

    public OnExceptionNoticeContition() {
        super("prometheus.exceptionnotice.enabled", true);
    }

}
