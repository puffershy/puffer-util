package com.puffer.util.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CONTENT3")
public class Content3 {

	private String name = "buyi";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
