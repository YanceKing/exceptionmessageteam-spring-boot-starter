package com.yance.anno;

import java.lang.annotation.*;


/**
 * @author yance
 * 异常监听
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ExceptionListener {

    /**
     * 出错了找谁？？？
     *
     * @return
     */
    String value() default "";
}
