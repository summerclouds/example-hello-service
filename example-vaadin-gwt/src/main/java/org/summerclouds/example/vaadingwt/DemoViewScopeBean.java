package org.summerclouds.example.vaadingwt;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class DemoViewScopeBean {
	public String getData() {
		return "Same bean instance for same view. bean="
				+ toString();

	}
}