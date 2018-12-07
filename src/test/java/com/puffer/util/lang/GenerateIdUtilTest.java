package com.puffer.util.lang;


import org.testng.annotations.Test;

/**
 * 工具{@link GenerateIdUtil}单元测试
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2018上午11:16:23
 */
public class GenerateIdUtilTest {

	@Test
	public void generateId() {
		//批量生成id
		
		for (int i = 0; i < 100; i++) {
			System.out.println(GenerateIdUtil.generateId());
		}
	}

}
