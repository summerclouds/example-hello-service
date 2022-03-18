package org.summerclouds.example.vaadingwt.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.summerclouds.example.vaadingwt.DemoUIScopeBean;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

//Annotation order is matter here, @UIScope is before @SpringView
@UIScope
@SpringView(name = UIScopeView.VIEW_NAME)
public class UIScopeView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3089511061636116441L;

	public static final String VIEW_NAME = "ui";

	@Autowired
	private DemoUIScopeBean uiBean;

	@PostConstruct
	void init() {
		addComponent(new Label("This is a UI scoped view."));
		addComponent(new Label("uiBean says: " + uiBean.getData()));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// This view is constructed in the init() method()
	}
}
