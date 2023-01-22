package com.example.application.views.registro;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.application.data.Role;
import com.example.application.data.entity.User;
import com.example.application.data.entity.Usuario;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
//import com.vaadin.data.validator.Validator;

@PageTitle("Registro")
@Route(value = "sign_up", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class RegistroView extends Div {

    private TextField username = new TextField("Username");
    private TextField name = new TextField("Nombre");
    private TextField surname = new TextField("Apellidos");
    private TextField dni = new TextField("DNI");
    private TextField phone = new TextField("Telefono");
    private TextField email = new TextField("Email");
    private PasswordField hashedPassword = new PasswordField("Contraseña");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
     
    private Binder<User> binder = new Binder<>(User.class);
    private Role usrRol;
    

    public RegistroView(UserService userService) {
        addClassName("registro-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        usrRol = Role.USER;
        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
        	BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        	hashedPassword.setValue(bcryptPasswordEncoder.encode(hashedPassword.getValue()));
        	binder.getBean().setRol(usrRol);
            userService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new User());
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(username, name, surname, dni, phone, email, hashedPassword);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    //public class telefonoValidator implements Validator<String> { }

}