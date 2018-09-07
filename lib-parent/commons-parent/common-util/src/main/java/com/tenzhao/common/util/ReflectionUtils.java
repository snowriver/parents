package com.tenzhao.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *  反射类公用方法
 * <p>
 * <pre>
 * @author chenxj
 * @Date   2014-9-18 下午2:20:25
 * @see     
 * @since
 */
public class ReflectionUtils {

	
	/**
	 * 循环向上转型, 获取对象的   DeclaredMethod 
     * @param object :         子类对象  
	 * @param methodName :     父类中的方法名    
	 * @param parameterTypes : 父类中的方法参数类型  
	 * @return                   父类中的方法对象    
	 */

	public static Method getDeclaredMethod(Object object, String methodName,
			Class<?>... parameterTypes) {
		Method method = null;
		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (Exception e) {
				//这里甚么都不要做！并且这里的异常必须这样写，不能抛出去
			}
		}
		return null;
	}

	/**
	 * <pre>
	 *   方法描述:调用实例对象上的方法
	 * </pre>
	 * @author chenxj
	 * @Date   2014-9-19 上午11:46:01
	 * @param object         实例对象
	 * @param methodName     方法名
	 * @param parameterTypes 参数类型
	 * @param parameters     实参
	 * @return
	 */
	public static Object invokeMethod(Object object, String methodName,
			Class<?>[] parameterTypes, Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		method.setAccessible(true);
		try {
			if (null != method) {
				return method.invoke(object, parameters);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField        
	 * @param object : 子类对象  
	 * @param fieldName : 父类中的属性名        
	 * @return 父类中的属性对象        
	 */
	public static Field getDeclaredField(Object object,String fieldName){ 
		Field field = null;
		Class<?> clazz = object.getClass();
		for(;clazz!= Object.class;clazz = clazz.getSuperclass()){
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return field; 
		
	}

	/**
	 *  直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter  
	 *  @param object : 子类对象    
	 *   @param fieldName : 父类中的属性名  
	 *  @param value : 将要设置的值  
	 */
	public static void setFieldValue(Object object, String fieldName,
			Object value) {
		Field field = getDeclaredField(object, fieldName);
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (Exception e) {

		}
	}

	/**
	 * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter  
	 * @param object : 子类对象  
     * @param fieldName : 父类中的属性名  
	 * @return : 父类中的属性值        
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
