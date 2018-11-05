package com.buyi.util.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * web工具类
 *
 * @author buyi
 * @date 2017年12月12日下午3:51:23
 * @since 1.0.0
 */
public class WebUtils {
	/**
	 * 判断是否ajax请求
	 *
	 * @author buyi
	 * @date 2017年5月5日下午11:52:14
	 * @since 1.0.0
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		if (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString())) {
			return true;
		}

		if (request.getHeader("Content-Type") != null && "application/json".equals(request.getHeader("Content-Type"))) {
			return true;
		}

		return false;
	}

	/**
	 * 响应json信息
	 *
	 * @author buyi
	 * @date 2017年12月12日下午4:21:59
	 * @since 1.0.0
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public static void sendJson(HttpServletResponse response, Object json) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(JSON.toJSONString(json));

	}

	// public static boolean isAjax(HttpServletRequest request, Object handler)
	// {
	// // Ajax请求默认为json请求
	// String requestEdwith = request.getHeader("X-Requested-With");
	// if (StringUtils.equalsIgnoreCase(requestEdwith, "XMLHttpRequest")) {
	// return true;
	// }
	//
	// // 请求url中包含.json或.jsonp的也认为是json请求
	// String requestUri = request.getRequestURI();
	// if (StringUtils.endsWithIgnoreCase(requestUri, ".json") ||
	// StringUtils.endsWithIgnoreCase(requestUri, ".jsonp")) {
	// return true;
	// }
	//
	// // handler如果带了@ResponseBody也认为是JSON请求
	// if (handler instanceof HandlerMethod) {
	// HandlerMethod hm = (HandlerMethod) handler;
	// // ResponseBody
	// if (hm.getMethod().getAnnotation(ResponseBody.class) != null) {
	// return true;
	// }
	// // RestController
	// if (AnnotationUtils.findAnnotation(hm.getBean().getClass(),
	// RestController.class) != null) {
	// return true;
	// }
	// }
	//
	// return false;
	// }
}
