package com.tong.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TestAnnotation注解，用于测试注解处理器
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface TestAnnotation {
}
