package com.example.application.views.usuarios;

import com.example.application.data.entity.Usuario;
import com.example.application.data.service.UsuarioService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Usuarios")
@Route(value = "usuarios/:usuarioID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UsuariosView extends Div implements BeforeEnterObserver {

    private final String USUARIO_ID = "usuarioID";
    private final String USUARIO_EDIT_ROUTE_TEMPLATE = "usuarios/%s/edit";

    private final Grid<Usuario> grid = new Grid<>(Usuario.class, false);
    
    private TextField filterText = new TextField();
    
    private TextField nombre;
    private TextField username;
    private TextField dni;
    private TextField telefono;
    private TextField email;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    private final Button delete = new Button("Borrar");
    
    private final BeanValidationBinder<Usuario> binder;

    private Usuario usuario;

    private final UsuarioService usuarioService;

    @Autowired
    public UsuariosView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        addClassNames("usuarios-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("username").setAutoWidth(true);
        grid.addColumn("dni").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.setItems(query -> usuarioService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(USUARIO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(UsuariosView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Usuario.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.usuario != null) {
                	binder.writeBean(this.usuario);
                    usuarioService.update(this.usuario);
                    Notification.show("Detalles de Usuario guardados");
                }else {
                	Notification.show("No se pueden generar usuario desde esta pagina, dirijase a registro.");
                }
                clearForm();
                refreshGrid(); 
                UI.getCurrent().navigate(UsuariosView.class);
            } catch (ValidationException validationException) {
                Notification.show("Ocurrió una excepción al intentar guardar los detalles del usuario.");
            }                 
        });
        delete.addClickListener(e ->{
	       	try {
	       		if(this.usuario != null) {
	       			binder.writeBean(this.usuario);
	       			usuarioService.delete(this.usuario.getId());
	       			Notification.show("Usuario Borrado");
	       		}
	       		clearForm();
	       		refreshGrid();
	       		UI.getCurrent().navigate(UsuariosView.class);
	       	} catch(ValidationException validationException) {
	       		Notification.show("Ocurrió una excepción al intentar eliminar al usuario.");
	       	}
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> usuarioId = event.getRouteParameters().get(USUARIO_ID).map(UUID::fromString);
        if (usuarioId.isPresent()) {
            Optional<Usuario> usuarioFromBackend = usuarioService.get(usuarioId.get());
            if (usuarioFromBackend.isPresent()) {
                populateForm(usuarioFromBackend.get());
            } else {
                Notification.show(String.format("The requested usuario was not found, ID = %s", usuarioId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(UsuariosView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nombre = new TextField("Nombre");
        username = new TextField("Username");
        dni = new TextField("Dni");
        telefono = new TextField("Telefono");
        email = new TextField("Email");
        formLayout.add(nombre, username, dni, telefono, email);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(save, delete, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(getToolbar(),grid);
    }
    
	private Component getToolbar() {
		// TODO Auto-generated method stub
		filterText.setWidth("500px");
		filterText.setPlaceholder("Filtra por cualquier dato");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());
		
		
		HorizontalLayout toolbar = new HorizontalLayout(filterText);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }
    
	private void updateList() {
		// TODO Auto-generated method stub
		grid.setItems(usuarioService.findAllUsuarios(filterText.getValue()));
	}

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Usuario value) {
        this.usuario = value;
        binder.readBean(this.usuario);

    }
}