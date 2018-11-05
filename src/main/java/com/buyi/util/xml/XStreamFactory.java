package com.buyi.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * xstream工厂
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午3:26:09
 */
public class XStreamFactory {

	private static XStream xstreamStaxDriver = null;
	/** 格式化驱动器 */
	private static XStream xstreamDomDriver = null;

	/**
	 * 获取StaxDriver的XStream实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:34:35
	 * @return
	 */
	public static XStream instanceXStremStaxDriver() {
		if (xstreamStaxDriver == null) {
			synchronized (XStreamFactory.class) {
				if (xstreamStaxDriver == null) {
					xstreamStaxDriver = new XStream(new StaxDriver());
					// 设置annotation支持
					xstreamStaxDriver.autodetectAnnotations(true);
					// 如果实体对象存在泛型变量，转换成字符串的时候，标签会增加class属性，设置null，就不会出现
					xstreamStaxDriver.aliasSystemAttribute(null, "class");
					// 忽略未知元素
					xstreamStaxDriver.ignoreUnknownElements();
				}
			}
		}

		return xstreamStaxDriver;
	}

	/**
	 * 获取DomDriver的XStream实例
	 * <p>
	 * 格式化输出
	 *
	 * @author buyi
	 * @date 2018年1月5日下午10:34:30
	 * @since 1.0.0
	 * @return
	 */
	public static XStream instanceXStreamDomDriver() {
		if (xstreamDomDriver == null) {
			synchronized (XStreamFactory.class) {
				if (xstreamDomDriver == null) {
					xstreamDomDriver = new XStream(new DomDriver());
					// 设置annotation支持
					xstreamDomDriver.autodetectAnnotations(true);
					// 如果实体对象存在泛型变量，转换成字符串的时候，标签会增加class属性，设置null，就不会出现
					xstreamStaxDriver.aliasSystemAttribute(null, "class");
					// 忽略未知元素
					xstreamDomDriver.ignoreUnknownElements();
				}
			}
		}

		return xstreamDomDriver;
	}

}
