package com.puffer.util.lang;


import com.google.common.base.CaseFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 字符格式转换器单元测试类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017上午10:37:25
 */
public class CaseFormatUtilTest {
	String camel1 = "className";
	String camel2 = "ClassName";
	String underScore1 = "class_name";
	String underScore2 = "class_Name";
	String underScore3 = "Class_name";
	String underScore4 = "Class_Name";
	String underScore5 = "CLASS_NAME";

	String underScorePoint = "class_name.name_sex";

	/**
	 * 驼峰转下滑线，并转换成小写单元测试
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午10:38:40
	 */
	@Test
	public void camelToLowerUnderScore() {
		Assert.assertEquals("class_name", CaseFormatUtil.camelToLowerUnderScore("className"));
		Assert.assertEquals("class_name", CaseFormatUtil.camelToLowerUnderScore("ClassName"));
		
		
		Assert.assertEquals("class_name_public", CaseFormatUtil.camelToLowerUnderScore("ClassName_public"));
		Assert.assertEquals("class_name__public", CaseFormatUtil.camelToLowerUnderScore("ClassName_Public"));
		
		Assert.assertEquals("class._name", CaseFormatUtil.camelToLowerUnderScore("Class.Name"));
		Assert.assertEquals("class.name", CaseFormatUtil.camelToLowerUnderScore("class.name"));
	}

	@Test
	public void camelToUpperUnderScore() {
		System.out.println(underScore1 + " --> " + CaseFormatUtil.underScoreToLowerCamel(underScore1));
		System.out.println(underScore2 + " --> " + CaseFormatUtil.underScoreToLowerCamel(underScore2));
		System.out.println(underScore3 + " --> " + CaseFormatUtil.underScoreToLowerCamel(underScore3));
		System.out.println(underScore4 + " --> " + CaseFormatUtil.underScoreToLowerCamel(underScore4));
		System.out.println(underScore5 + " --> " + CaseFormatUtil.underScoreToLowerCamel(underScore5));

		System.out.println("* " + underScore1 + " --> " + CaseFormatUtil.underScoreToUpperCamel(underScore1));
		System.out.println("* " + underScore2 + " --> " + CaseFormatUtil.underScoreToUpperCamel(underScore2));
		System.out.println("* " + underScore3 + " --> " + CaseFormatUtil.underScoreToUpperCamel(underScore3));
		System.out.println("* " + underScore4 + " --> " + CaseFormatUtil.underScoreToUpperCamel(underScore4));
		System.out.println("* " + underScore5 + " --> " + CaseFormatUtil.underScoreToUpperCamel(underScore5));
	}

	@Test
	public void testSpaceToUpperFirstChar(){
		String  str = "hello world say Hi Pu buyi";
		System.out.println(CaseFormatUtil.spaceToUpperFirstChar(str));
//		System.out.println(CaseFormat.UPPER_CAMEL);
	}
}
