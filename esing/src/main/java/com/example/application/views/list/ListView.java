package com.example.application.views.list;
import com.example.application.data.Role;
import com.example.application.data.entity.User;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@PageTitle("List")
@Route("/lista")
@AnonymousAllowed
public class ListView extends VerticalLayout {
	
	
	Grid<User> grid = new Grid<>(User.class);
	TextField filterText = new TextField();
	UserForm form;
	private UserService service;
	
	public ListView(UserService service) {
		this.service = service;
		addClassName("list-view");
		setSizeFull();
		
		configureGrid();
		configureForm();
		
		add(
			getToolbar(),
			getContent()
		);
		
		updateList();
	}

	private void updateList() {
		// TODO Auto-generated method stub
		grid.setItems(service.findAllUsers(filterText.getValue()));
	}

	private Component getContent() {
		// TODO Auto-generated method stub
		HorizontalLayout content = new HorizontalLayout(grid, form);
		content.setFlexGrow(2,grid);
		content.setFlexGrow(1,form);
		content.addClassName("content");
		content.setSizeFull();
		
		return content;
	}

	private void configureForm() {
		// TODO Auto-generated method stub
		form = new UserForm();
		form.setWidth("25em");
		
	}

	private Component getToolbar() {
		// TODO Auto-generated method stub
		
		filterText.setPlaceholder("Filter by anything");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());
		
		Button addUserButton = new Button("Add user");
		
		HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	private void configureGrid() {
		// TODO Auto-generated method stub
		grid.addClassName("user-grid");
		grid.setSizeFull();
		grid.setColumns("username","name","dni","email","address","phone");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
	}
}