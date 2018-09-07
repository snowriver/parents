package com.tenzhao.common.excel.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 标识一个数据导入的类,可在类或方法上注解
 * 根据导入时的写法而定，如果所有数据的导入方式都写在一个类的不同方法中，则可注解在方法
 * 如果用不同类来实现操作不同导入，则注解放在类上
 * </pre>
 * @author chenxj
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoDataImpClass {
	/**
	 * 这个值用于标识一个Xml模板中bean的id
	 * @return
	 */
	 String value() default "";
}
