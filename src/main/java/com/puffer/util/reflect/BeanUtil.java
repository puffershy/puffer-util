package com.puffer.util.reflect;

import java.lang.reflect.Field;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 反射工具类
 *
 * @author buyi
 * @date 2017年8月31日下午10:42:44
 * @since 1.0.0
 */
public class BeanUtil {

	private static final Field[] NO_FIELDS = {};

	/** 缓存对象实例域 */
	private static final Map<Class<?>, Field[]> declaredFieldsCache = Maps.newConcurrentMap();

	/**
	 * 获取对象实例集合
	 * 
	 * @author buyi
	 * @date 2017年8月31日下午10:46:08
	 * @since 1.0.0
	 * @param obj
	 * @return
	 */
	public static Field[] getDeclaredFields(Object obj) {
		return getDeclaredFields(obj.getClass());
	}

	/**
	 * 根据类类型获取实例域集合
	 *
	 * @author buyi
	 * @date 2017年8月31日下午10:51:01
	 * @since 1.0.0
	 * @param clazz
	 * @return
	 */
	public static Field[] getDeclaredFields(Class<?> clazz) {
		Field[] result = declaredFieldsCache.get(clazz);
		if (result == null) {
			result = clazz.getDeclaredFields();
			for (Field field : result) {
				field.setAccessible(true);
			}

			declaredFieldsCache.put(clazz, (result.length == 0 ? NO_FIELDS : result));
		}

		return result;
	}

	/**
	 * 实体对象转换成map
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:29:34
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Map<String, Object> toMap(Object obj) throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = Maps.newHashMap();
		Field[] fields = getDeclaredFields(obj.getClass());
		for (Field field : fields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}

		return map;
	}

}
