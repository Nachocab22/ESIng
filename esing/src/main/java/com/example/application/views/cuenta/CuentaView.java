package com.example.application.views.cuenta;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;
import com.example.application.data.service.CuentaService;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Asige una cuenta")
@Route(value = "admin-cuenta", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CuentaView extends Div {

	private Cuenta cuenta = new Cuenta();
	private final UserService userService;
    private TextField iban = new TextField("Número de cuenta");
    private Select<String> mote = new Select<>();
    private Select<User> titular = new Select<>();
    private Tarjeta tarjetas = null;
    
    private final BeanValidationBinder<Cuenta> binder;

    private Button cancel = new Button("Cancelar");
    private Button submit = new Button("Enviar");

    @Autowired
    public CuentaView(CuentaService cuentaService, UserService userService) {
        addClassName("asignar-cuenta");
        this.userService = userService;

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        
        binder = new BeanValidationBinder<>(Cuenta.class);
		
		binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            Notification.show("Not implemented");
        });
        submit.addClickListener(e -> {
        	try {
        		binder.writeBean(this.cuenta);
        		cuentaService.update(this.cuenta);
        		
        		clearForm();
        		Notification.show("Cuenta asignada correctamente");
        		
        	} catch (ValidationException e1) {
        		Notification.show("No se pudo asignar la cuenta, intentelo de nuevo más tarde");
        	}
            
        });
    }

    private Component createTitle() {
        return new H3("Asignación de cuenta");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(titular, iban, mote);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        iban.setPlaceholder("ES21 1465 0100 72 2030876293");
        iban.setPattern("[\\d ]*");
        iban.setRequired(true);
        mote.setLabel("Tipo de cuenta");
        mote.setPlaceholder("Tipo de cuenta");
        mote.setItems("Cuenta 123", "Cuenta ESIng premium", "Cuenta ESIng consumer", "Cuenta Business", "Cuenta Business Pro");
        titular.setLabel("Titular");
        titular.setPlaceholder("Cliente");
        titular.setItemLabelGenerator(User::getFullName);
        
        final List<User> users = userService.findAllUsers(null);
        
        titular.setItems(users);
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(submit);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
    
    private void clearForm() {
        populateForm(cuenta);
    }

    private void populateForm(Cuenta value) {
        this.cuenta = value;
        binder.readBean(this.cuenta);

    }

}
