package com.example.application.views.list;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.application.data.Role;
import com.example.application.data.entity.User;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class UserForm extends FormLayout {
	Binder<User> binder = new BeanValidationBinder<>(User.class);
	private User user = new User();
	
	
	TextField username = new TextField("username");
	TextField name = new TextField("name");
	//TextField dni = new TextField("dni");
	//TextField email = new TextField("email");
	//TextField address = new TextField("address");
	//NumberField phone = new NumberField("phone");
	PasswordField hashedPassword = new PasswordField("password");//FRW
	Set<Role> usrRol;//FRW
	
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancel = new Button("Cancel");
	
	public UserForm() {
		
		addClassName("user-form");
		binder.bindInstanceFields(this);
		
		
		 add(
			username,
			name,
			//email,
			//address,
			//phone,
			hashedPassword,
			createButtonLayout()
		);
		 
	}
	
	public void setUser(User user) {
		this.user = user;
		binder.readBean(user);
		
	}

	private Component createButtonLayout() {
		// TODO Auto-generated method stub
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickListener(event -> validateAndSave());
		delete.addClickListener(event -> fireEvent(new DeleteEvent(this,user)));
		cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
		
		save.addClickShortcut(Key.ENTER);
		cancel.addClickShortcut(Key.ESCAPE);
		
		return new HorizontalLayout(save, delete, cancel);
	}
	
	private void validateAndSave() {
		user.setName(name.toString());
		user.setUsername(username.toString());
        usrRol = new HashSet<Role>(); //FRW
        usrRol.add(Role.USER); //FRW
        user.setRoles(usrRol);
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();//FRW
    	hashedPassword.setValue(bcryptPasswordEncoder.encode(hashedPassword.getValue()));//FRW
		user.setHashedPassword(hashedPassword.getValue());
    	// TODO Auto-generated method stub
		try {
			binder.writeBean(user);
			System.out.println(binder);
			//userService.update(user);
			
			fireEvent(new SaveEvent(this,user));
		}catch(ValidationException e) {
			e.printStackTrace();
		}
	}

	// Events
	public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
	  private User user;

	  protected UserFormEvent(UserForm source, User user) { 
	    super(source, false);
	    this.user = user;
	  }

	  public User getUser() {
	    return user;
	  }
	}

	public static class SaveEvent extends UserFormEvent {
	  SaveEvent(UserForm source, User user) {
	    super(source, user);
	  }
	}

	public static class DeleteEvent extends UserFormEvent {
	  DeleteEvent(UserForm source, User user) {
	    super(source, user);
	  }

	}

	public static class CloseEvent extends UserFormEvent {
	  CloseEvent(UserForm source) {
	    super(source, null);
	  }
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
	    ComponentEventListener<T> listener) { 
	  return getEventBus().addListener(eventType, listener);
	}
	
}