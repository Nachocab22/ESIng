package com.example.application.views.incidencia;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.application.data.entity.Incidencia;
import com.example.application.data.entity.User;
import com.example.application.data.service.IncidenciaService;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Nuevo comunicado")
@Route(value = "incidence-form", layout = MainLayout.class)
@RolesAllowed("USER")
public class IncidenciaView extends Div{

	private TextField motivo = new TextField("Motivo");
	private Checkbox incidencia = new Checkbox("Incidencia", false);
	private TextArea descripcion = new TextArea("Describanos su problema:");
	private int charLimit = 1000;
	
	protected final IncidenciaService service;
	private Incidencia i = new Incidencia();
	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	String name = authentication.getName();
	private final BeanValidationBinder<Incidencia> binder;
	
	private Button submit = new Button("Enviar");
	
	@Autowired
	public IncidenciaView(IncidenciaService service, UserService userService) {
		this.service = service;
		addClassName("incidencia-view");
		
		add(createTitle());
		add(createFormLayout());
		add(createButtonLayout());
		
		binder = new BeanValidationBinder<>(Incidencia.class);
		
		binder.bindInstanceFields(this);
		
		descripcion.addValueChangeListener(e -> {
			e.getSource().setHelperText(e.getValue().length() + "/" + charLimit);
		});
		
		submit.addClickListener(q -> {
			try {
				binder.writeBean(this.i);
				this.i.setAutor(userService.getCurrentUser());
				System.out.println(this.i.getAutor().getName());
				
				service.update(this.i);
				
				clearForm();
				Notification.show("Su mensaje ha sido enviado correctamente.");
				UI.getCurrent().navigate(IncidenciaView.class);
			} catch (ValidationException e1) {
				Notification.show("Ha ocurrido un error al enviar la incidencia, inténtelo de nuevo más tarde.");
			}
		});
	}

	private Component createTitle() {
		return new H3("   Abrir nuevo comunicado");
	}
	
	private Component createFormLayout() {
		FormLayout formLayout = new FormLayout();
		formLayout.add(motivo, incidencia, descripcion);
		formLayout.setColspan(descripcion, 2);
		return formLayout;
	}
	
	private Component createButtonLayout() {
		HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        motivo.setPlaceholder("Introduzca un motivo");
        motivo.setRequired(true);
        motivo.setErrorMessage("Por favor, introduzca un motivo.");
        descripcion.setWidthFull();
        descripcion.setRequired(true);
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(submit);
        return buttonLayout;
	}
	
	private void clearForm() {
        populateForm(i);
    }

    private void populateForm(Incidencia value) {
        this.i = value;
        binder.readBean(this.i);

    }
}
