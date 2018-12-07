package com.puffer.util.xml;

import javax.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;

/**
 * {@link XMLUtil} 单元测试类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午2:07:27
 */
public class XMLUtilTest {

	@Test
	public void toXML() throws XMLStreamException {
		Base base = new Base();
		base.setReqMsg("0000");
		base.setRemark("中文描述");

		Content content = new Content();
		content.setCity("福建");
		content.setName("buyi");

		base.setObj(content);

		String xml = XMLUtil.toXML(base);
		System.out.println(xml);

		Content2 content2 = new Content2();
		content2.setAge("12");
		content2.setSex("");

		Base base2 = new Base();
		base2.setReqMsg("0000");
		base2.setRemark("中文描述");
		base2.setObj(content2);

		base2.setList(Lists.newArrayList(new Content3()));

		System.out.println();

		xml = XMLUtil.toXMLWithoutDeclare(base2);
		System.out.println(xml);
	}

}
