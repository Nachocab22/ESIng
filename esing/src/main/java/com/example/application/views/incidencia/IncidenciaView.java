package com.example.application.views.incidencia;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.application.data.entity.Incidencia;
import com.example.application.data.service.IncidenciaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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

	private TextField subject = new TextField("Motivo");
	private ComboBox<String> type = new ComboBox<>("Tipo de consulta");
	private TextArea description = new TextArea("Describanos su problema:");
	private int charLimit = 1000;
	
	private final IncidenciaService service;
	private Incidencia i = new Incidencia();
	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	String name = authentication.getName();
	private final BeanValidationBinder<Incidencia> binder;
	
	private Button submit = new Button("Enviar");
	
	@Autowired
	public IncidenciaView(IncidenciaService service) {
		this.service = service;
		addClassName("incidencia-view");
		
		add(createTitle());
		add(createFormLayout());
		add(createButtonLayout());
		
		binder = new BeanValidationBinder<>(Incidencia.class);
		
		description.addValueChangeListener(e -> {
			e.getSource().setHelperText(e.getValue().length() + "/" + charLimit);
		});
		
		submit.addClickListener(q -> {
			i.setAutor(authentication.getName());
			i.setMotivo(subject.getValue());
			if(type.getValue() == "Incidencia")
				i.setIncidencia(true);
			else
				i.setIncidencia(false);
			i.setDescripcion(description.getValue());
			service.update(i);
			try {
				binder.writeBean(this.i);
				service.update(this.i);
				clearForm();
				Notification.show("Su mensaje ha sido enviado correctamente.");
				UI.getCurrent().navigate(IncidenciaView.class);
			} catch (ValidationException e1) {
				Notification.show("Ha ocurrido un error al enviar la incidencia, intentelo de nuevo más tarde.");
			}
		});
	}

	private Component createTitle() {
		return new H3("   Abrir nuevo comunicado");
	}
	
	private Component createFormLayout() {
		FormLayout formLayout = new FormLayout();
		formLayout.add(subject, type, description);
		formLayout.setColspan(description, 2);
		return formLayout;
	}
	
	private Component createButtonLayout() {
		HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        subject.setPlaceholder("Introduzca un motivo");
        subject.setRequired(true);
        subject.setErrorMessage("Por favor, introduzca un motivo.");
        type.setPlaceholder("Seleccione un tipo");
        type.setItems("Consulta", "Incidencia");
        description.setWidthFull();
        description.setRequired(true);
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
