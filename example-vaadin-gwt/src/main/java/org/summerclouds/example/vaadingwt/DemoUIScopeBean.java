package org.summerclouds.example.vaadingwt;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class DemoUIScopeBean {
	public String getData() {
		return "Same bean instance for same UI. bean=" + toString();
	}
}