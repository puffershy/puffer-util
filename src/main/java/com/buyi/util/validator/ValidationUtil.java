package com.buyi.util.validator;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.parameternameprovider.ParanamerParameterNameProvider;

/**
 * <p>
 * 基于JSR303的JavaBean参数校验工具类
 * </p>
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午5:32:26
 */
public class ValidationUtil {

	/**
	 * 基础校验器
	 */
	private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
			.parameterNameProvider(new ParanamerParameterNameProvider()).buildValidatorFactory().getValidator();

	/**
	 * 参数校验
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:46:30
	 * @param obj
	 * @return
	 */
	public static <T> ValidateResult validate(T obj) {
		Set<ConstraintViolation<T>> validate = validator.validate(obj);
		if (validate.isEmpty()) {
			// 如果校验结果集为空，则表示校验通过
			return ValidateResult.newOk();
		}

		// 校验不通过

		ValidateResult validateResult = ValidateResult.newNotOk();

		Iterator<ConstraintViolation<T>> iter = validate.iterator();
		while (iter.hasNext()) {
			ConstraintViolation<T> cv = iter.next();
			// String argsName = cv.getPropertyPath().toString();
			validateResult.errMsg(cv.getMessage());
		}

		return validateResult;
	}
}
