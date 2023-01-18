package com.example.application.views.registro;
import com.example.application.data.Role;
import com.example.application.data.entity.Usuario;
import com.example.application.data.service.UsuarioService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
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

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import com.vaadin.data.validator.Validator;

@PageTitle("Registro")
@Route(value = "sign_up", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class RegistroView extends Div {

    private TextField username = new TextField("Username");
    private TextField nombre = new TextField("Nombre");
    private TextField dni = new TextField("DNI");
    private TextField telefono = new TextField("Telefono");
    private TextField email = new TextField("Email");
    private PasswordField hashedPassword = new PasswordField("Contraseña");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
     
    private Binder<Usuario> binder = new Binder<>(Usuario.class);
    private Set<Role> usrRol;
    

    public RegistroView(UsuarioService usuarioService) {
        addClassName("registro-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        usrRol = new HashSet<Role>();
        usrRol.add(Role.USER);
        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
        	BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        	hashedPassword.setValue(bcryptPasswordEncoder.encode(hashedPassword.getValue()));
        	binder.getBean().setRoles(usrRol);
            usuarioService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new Usuario());
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(username, nombre, dni, telefono, email, hashedPassword);
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