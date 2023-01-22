package com.example.application.views.tarjeta;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;
import com.example.application.data.service.CuentaService;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Introduce tarjeta de credito")
@Route(value = "admin-tarjeta", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class TarjetaView extends Div {

	private Select<Integer> mes = new Select<>();
	private Select<Integer> año = new Select<>();
	
    private TextField numero = new TextField("Número de la tarjeta");
    private Select<User> titular = new Select<>();
    private Select<Cuenta> cuenta = new Select<>();
    private ExpirationDateField caducidad = new ExpirationDateField("Fecha de caducidad", mes, año);
    private PasswordField cvv = new PasswordField("CVV");
    
    private Tarjeta tarjeta = new Tarjeta();
	private final UserService userService;
	private final CuentaService cuentaService;
	private final BeanValidationBinder<Tarjeta> binder;

    private Button cancel = new Button("Cancelar");
    private Button submit = new Button("Enviar");
    
    @Autowired
    public TarjetaView(TarjetaService tarjetaService, UserService userService, CuentaService cuentaService) {
        addClassName("tarjeta-view");
        this.cuentaService = cuentaService;
        this.userService = userService;

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        
		binder = new BeanValidationBinder<>(Tarjeta.class);
		
		binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            Notification.show("Not implemented");
        });
        submit.addClickListener(e -> {
        	try {
        		binder.writeBean(this.tarjeta);
    			tarjetaService.update(this.tarjeta);
                Notification.show("La tarjeta se ha asignado correctamente");
        	} catch(ValidationException e1) {
        		Notification.show("Ha ocurrido un error al asignar la tarjeta, inténtelo de nuevo más tarde.");
        	}
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
        titular.setItemLabelGenerator(User::getUsername);
        
        final List<User> users = userService.findAllUsers(null);
        
        titular.setItems(users);
        cuenta.setLabel("Cuenta");
        cuenta.setPlaceholder("Seleccione la cuenta");
        titular.addValueChangeListener(event -> {
        	cuenta.setItemLabelGenerator(Cuenta::getMote);
        	final List<Cuenta> cuentas = cuentaService.findAllCuentas(event.getValue().getId().toString());
            cuenta.setItems(cuentas);
        });
        
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