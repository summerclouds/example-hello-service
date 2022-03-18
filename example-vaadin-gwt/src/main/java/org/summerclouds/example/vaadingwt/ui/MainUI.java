package org.summerclouds.example.vaadingwt.ui;

import org.summerclouds.example.vaadingwt.view.ErrorView;
import org.summerclouds.example.vaadingwt.view.UIScopeView;
import org.summerclouds.example.vaadingwt.view.ViewScopeView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI(path = MainUI.APP_ROOT)
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

	static final String APP_ROOT = "/vaadin-spring-demo";
	private static final String VIEW_SCOPED_VIEW = "View_Scoped View";
	private static final String UI_SCOPED_VIEW = "UI_Scoped View";
	private static final long serialVersionUID = 4967383498113318791L;
	private Panel springViewDisplay;

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		setContent(root);

		final CssLayout navigationBar = new CssLayout();
		
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	
		navigationBar.addComponent(createNavigationButton(UI_SCOPED_VIEW, UIScopeView.VIEW_NAME));
		navigationBar.addComponent(new Label(" "));
		navigationBar.addComponent(createNavigationButton(VIEW_SCOPED_VIEW, ViewScopeView.VIEW_NAME));
	
		root.addComponent(navigationBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		getNavigator().setErrorView(ErrorView.class);
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
	
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

}