package com.example.application.views.introducetarjetadecredito;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.User;
import com.example.application.data.service.TarjetaService;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Introduce tarjeta de credito")
@Route(value = "admin-tarjeta", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class IntroducetarjetadecreditoView extends Div {

	private final UserService userService;
	private Select<Integer> mes = new Select<>();
	private Select<Integer> año = new Select<>();
	
    private TextField numero = new TextField("Número de la tarjeta");
    private Select<User> titular = new Select<>();
    private Select<Cuenta> cuenta = new Select<>();
    private ExpirationDateField caducidad = new ExpirationDateField("Fecha de caducidad", mes, año);
    private PasswordField cvv = new PasswordField("CVV");

    private Button cancel = new Button("Cancelar");
    private Button submit = new Button("Enviar");

    public IntroducetarjetadecreditoView(UserService userService, TarjetaService tarjetaService) {
        addClassName("tarjeta-view");
        this.userService = userService;

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        cancel.addClickListener(e -> {
            Notification.show("Not implemented");
        });
        submit.addClickListener(e -> {
            Notification.show("Not implemented");
        });
    }

    private Component createTitle() {
        return new H3("Tarjeta de Crédito");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(numero, titular, cuenta, caducidad, cvv);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        numero.setPlaceholder("1234 5678 9123 4567");
        numero.setPattern("[\\d ]*");
        numero.setRequired(true);
        numero.setErrorMessage("Por favor introduzca un numero de tarjeta valido");
        titular.setLabel("Titular");
        titular.setPlaceholder("Seleccione el titular");
        titular.setItemLabelGenerator(User::getFullName);
        
        final List<User> users = userService.findAllUsers(SecurityContextHolder.getContext().getAuthentication().getName());
        
        titular.setItems(users);
        cuenta.setLabel("Cuenta");
        cuenta.setPlaceholder("Seleccione la cuenta");
        
        
        mes.setPlaceholder("Mes");
        mes.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        año.setPlaceholder("Año");
        año.setItems(20, 21, 22, 23, 24, 25);
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(submit);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private class ExpirationDateField extends CustomField<String> {
        public ExpirationDateField(String label, Select<Integer> month, Select<Integer> year) {
            setLabel(label);
            HorizontalLayout layout = new HorizontalLayout(month, year);
            layout.setFlexGrow(1.0, month, year);
            month.setWidth("100px");
            year.setWidth("100px");
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            // Unused as month and year fields part are of the outer class
            return "";
        }

        @Override
        protected void setPresentationValue(String newPresentationValue) {
            // Unused as month and year fields part are of the outer class
        }

    }

}