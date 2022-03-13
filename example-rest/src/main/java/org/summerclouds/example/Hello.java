package org.summerclouds.example;

import org.summerclouds.common.core.security.ISubject;
import org.summerclouds.common.core.tool.MSecurity;
import org.summerclouds.common.core.tool.MString;

public class Hello {

	private final long id;
	private final String content;
	private final String user = MString.toString(MSecurity.getCurrent());

	public Hello(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
	public String getUser() {
		return user;
	}
	
}
