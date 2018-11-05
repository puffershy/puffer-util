package com.buyi.util.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("content")
public class Content {
	@XStreamAlias("name")
	public String name;
	@XStreamAlias("city")
	public String city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
