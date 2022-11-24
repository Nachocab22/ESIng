package com.example.application.views.registro;
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

@PageTitle("Registro")
@Route(value = "sign_in", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class RegistroView extends Div {

    private TextField username = new TextField("Username");
    private TextField name = new TextField("Name");
    private PasswordField hashedPassword = new PasswordField("Password");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
     
    private Binder<User> binder = new Binder<>(User.class);
    private Set<Role> usrRol;
    

    public RegistroView(UserService userService) {
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
        formLayout.add(username, name, hashedPassword);
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

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        private TextField number = new TextField();

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

}
