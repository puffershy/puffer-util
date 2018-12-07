package com.puffer.util.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author buyi
 * @since 1.0.0
 * @date 2017下午4:22:28
 */
@XStreamAlias("root")
public class Base {
	@XStreamAlias("req_msg")
	private String reqMsg;

	@XStreamAlias("plain")
	private Object obj;
	
	private String remark;

	@XStreamImplicit
	private List list;

	public String getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
