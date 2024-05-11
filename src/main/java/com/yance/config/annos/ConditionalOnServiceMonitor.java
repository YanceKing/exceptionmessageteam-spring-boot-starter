package com.yance.config.annos;

import com.yance.config.conditions.OnServiceMonitorNoticeCondition;
import com.yance.config.conditions.PrometheusEnabledCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 
 * 判断开启服务监控
 * 
 * @author yance
 * @date 2020年1月6日
 * @version 1.0
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({ PrometheusEnabledCondition.class, OnServiceMonitorNoticeCondition.class })
public @interface ConditionalOnServiceMonitor {

}
