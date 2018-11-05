package com.buyi.util.validator;

import com.buyi.util.validator.ValidateResult;
import com.buyi.util.validator.ValidationUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 参数校验工具单元测试
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午7:03:11
 */
public class ValidationUtilTest {

	@Test
	public void validate() {
		Demo demo = new Demo();
		ValidateResult validate = ValidationUtil.validate(demo);
		Assert.assertFalse(validate.isOk());
		validate = ValidationUtil.validate(demo.setName("buyi"));
		Assert.assertTrue(validate.isOk());
	}

	private class Demo {
		@NotBlank(message = "name不能为空")
		private String name;

		public String getName() {
			return name;
		}

		public Demo setName(String name) {
			this.name = name;
			return this;
		}
	}

}
