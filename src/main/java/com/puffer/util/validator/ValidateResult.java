package com.puffer.util.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Lists;

/**
 * <p>
 * 参数绑定校验结果容器
 * </p>
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午6:17:19
 */
public class ValidateResult {
	/**
	 * 校验结果，true表示通过；false-表示不通过
	 */
	private boolean isOk;
	/**
	 * 校验异常信息集合
	 */
	private List<String> errorMessages;

	private ValidateResult(boolean isOk) {
		this.isOk = isOk;
	}

	private ValidateResult(boolean isOk, List<String> errorMessages) {
		this.isOk = isOk;
		this.errorMessages = errorMessages;
	}

	/**
	 * 新建一个通过的结果实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:21:18
	 * @return
	 */
	public static ValidateResult newOk() {
		return new ValidateResult(true);
	}

	/**
	 * 创建一个不通过的实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:45:15
	 * @return
	 */
	public static ValidateResult newNotOk() {
		return new ValidateResult(false, new ArrayList<String>());
	}

	/**
	 * 新建一个实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:19:33
	 * @param isOk
	 * @return
	 */
	public static ValidateResult newInstance(boolean isOk) {
		return new ValidateResult(isOk);
	}

	/**
	 * 新建一个实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:19:33
	 * @param isOk
	 * @param errorMessage
	 * @return
	 */
	public static ValidateResult newInstance(boolean isOk, List<String> errorMessages) {
		return new ValidateResult(isOk, errorMessages);
	}

	/**
	 * 设置异常信息
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:44:18
	 * @param msg
	 * @return
	 */
	public ValidateResult errMsg(String msg) {
		if (errorMessages == null) {
			errorMessages = Lists.newArrayList();
		}

		errorMessages.add(msg);
		return this;
	}

	/**
	 * 判断是否检验通过
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:49:26
	 * @return
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * 获取校验异常信息集合
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:49:40
	 * @return
	 */
	public List<String> getErrorMessages() {
		return errorMessages;
	}

	/**
	 * 获取第一个异常信息
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午6:53:28
	 * @return
	 */
	public String getErrMsgFirst() {
		if (CollectionUtils.isEmpty(getErrorMessages())) {
			return "";
		}

		return getErrorMessages().get(0);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
