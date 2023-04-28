package cn.ttitcn.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
	/**
	 * 部门表的别名
	 */
	public String deptAlias() default "";

	/**
	 * 用户表的别名
	 */
	public String userAlias() default "";
}
