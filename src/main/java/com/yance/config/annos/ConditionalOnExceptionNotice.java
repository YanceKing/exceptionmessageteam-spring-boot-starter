package com.yance.config.annos;

import com.yance.config.conditions.OnExceptionNoticeContition;
import com.yance.config.conditions.PrometheusEnabledCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 判断已开启异常通知
 * 
 * @author yangpeng
 * @date 2019年4月16日
 * @version 1.0
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({ PrometheusEnabledCondition.class, OnExceptionNoticeContition.class })
public @interface ConditionalOnExceptionNotice {

}
