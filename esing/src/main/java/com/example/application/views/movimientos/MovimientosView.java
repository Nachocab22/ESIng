package com.example.application.views.movimientos;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;
import com.example.application.data.service.CuentaService;
import com.example.application.data.service.MovimientoService;
import com.example.application.data.service.TarjetaService;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Movimientos")
@Route(value = "movimientos/:movimientoID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class MovimientosView extends Div implements BeforeEnterObserver {

	private final String MOVIMIENTO_ID = "movimientoID";
    private final String MOVIMIENTO_EDIT_ROUTE_TEMPLATE = "movimientos/%s/edit";

    private final Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);

    private Select<Cuenta> cuenta = new Select<>();
    private Select<Tarjeta> tarjeta = new Select<>();
    private Checkbox retenido;
    private TextField concepto;
    private NumberField cantidad;
    private DatePicker fecha_op;
    private ComboBox<String> tipo = new ComboBox<>("Tipo de movimiento");

    private final Button cancelar = new Button("Cancelar");
    private final Button guardar = new Button("Guardar");
    private final Button eliminar = new Button("Eliminar");

    private final BeanValidationBinder<Movimiento> binder;

    private Movimiento movimiento;

    private final MovimientoService movimientoService;
	private final CuentaService cuentaService;
	private final UserService userService;
	private final TarjetaService tarjetaService;

    @Autowired
    public MovimientosView(MovimientoService movimientoService, CuentaService cuentaService, UserService userService, TarjetaService tarjetaService) {
        this.movimientoService = movimientoService;
        this.cuentaService = cuentaService;
        this.userService = userService;
        this.tarjetaService = tarjetaService;
        addClassNames("movimientos-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("cuenta").setAutoWidth(true);
        grid.addColumn("tarjeta").setAutoWidth(true);
        grid.addColumn("retenido").setAutoWidth(true);
        grid.addColumn("concepto").setAutoWidth(true);
        grid.addColumn("cantidad").setAutoWidth(true);
        grid.addColumn("fecha_op").setAutoWidth(true);
        grid.addColumn("tipo").setAutoWidth(true);
        grid.setItems(query -> movimientoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(MOVIMIENTO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MovimientosView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Movimiento.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancelar.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        
        guardar.addClickListener(e -> {
            try {
                if (this.movimiento == null) {
                    this.movimiento = new Movimiento();
                }
                if (cuenta.isEmpty()) {
			        Notification.show("Falta introducir uno o más valores");
			    } else if (tarjeta.isEmpty()) {
			        Notification.show("Falta introducir uno o más valores");
			    } else if (concepto.isEmpty()) {
			        Notification.show("Falta introducir uno o más valores");
			    } else if (cantidad.isEmpty()) {
			        Notification.show("Falta introducir uno o más valores");
			    } else if (fecha_op.isEmpty()) {
			        Notification.show("Falta introducir uno o más valores");
			    } else if (tipo.isEmpty()) {
			        Notification.show("Falta introducir uno o más valores");
			    } else {
                binder.writeBean(this.movimiento);
                movimientoService.update(this.movimiento);
                clearForm();
                refreshGrid();
                Notification.show("Movimiento guardado.");
                UI.getCurrent().navigate(MovimientosView.class);
			    }} catch (ValidationException validationException) {
                Notification.show("ha ocurrido una excepción al intentar guardar el movimiento.");
            }
        });
        
        eliminar.addClickListener(e -> {
			try {
				binder.getBean();
				movimientoService.delete(this.movimiento.getId());

				clearForm();
				refreshGrid();
				
				Notification.show("Movimiento eliminado correctamente.");
				
				UI.getCurrent().navigate(MovimientosView.class);
			} catch (Exception exception) {
				Notification.show("No se pudo borrar el movimiento seleccionado.");
			}
		});
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> movimientoId = event.getRouteParameters().get(MOVIMIENTO_ID).map(UUID::fromString);
        if (movimientoId.isPresent()) {
            Optional<Movimiento> movimientoFromBackend = movimientoService.get(movimientoId.get());
            if (movimientoFromBackend.isPresent()) {
                populateForm(movimientoFromBackend.get());
            } else {
                Notification.show(String.format("The requested movimiento was not found, ID = %s", movimientoId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // cuando no esta disponible una linea seleccionada,
                // refresca el grid
                refreshGrid();
                event.forwardTo(MovimientosView.class);
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
        
        cuenta.setLabel("Cuenta");
		cuenta.setItemLabelGenerator(Cuenta::getMote);
		final List<Cuenta> cuentas = cuentaService.findAllCuentas(null);
		cuenta.setItems(cuentas);
		tarjeta.setLabel("Tarjeta");
		tarjeta.setItemLabelGenerator(Tarjeta::getNumero);
		final List<Tarjeta> tarjetas = tarjetaService.findAllTarjetas(null);
		tarjeta.setItems(tarjetas);
        retenido = new Checkbox("Retenido");
        retenido.setLabel("El movimiento está retenido");
        concepto = new TextField("Concepto");
        cantidad = new NumberField("Cantidad");
        fecha_op = new DatePicker("Fecha");
        tipo.setPlaceholder("Seleccione un tipo");
        tipo.setItems("Ingreso", "Gasto");
        formLayout.add(cuenta, tarjeta, concepto, cantidad, fecha_op, tipo, retenido);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        eliminar.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(guardar, cancelar, eliminar);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Movimiento value) {
        this.movimiento = value;
        binder.readBean(this.movimiento);

    }
}