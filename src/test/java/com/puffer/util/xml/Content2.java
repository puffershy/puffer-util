package com.puffer.util.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("cot")
public class Content2 {

	@XStreamAlias("sex")
	private String sex;
	@XStreamAlias("ageaa")
	private String age;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
