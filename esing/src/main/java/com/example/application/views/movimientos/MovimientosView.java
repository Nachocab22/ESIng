package com.example.application.views.movimientos;

import com.example.application.data.entity.Movimiento;
import com.example.application.data.service.MovimientoService;
import com.example.application.views.MainLayout;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
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

@PageTitle("Movimientos")
@Route(value = "movimiento/:movimientoID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class MovimientosView extends Div implements BeforeEnterObserver {

    private final String MOVIMIENTO_ID = "movimientoID";
    private final String MOVIMIENTO_EDIT_ROUTE_TEMPLATE = "movimientos/%s/edit";

    private final Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);

    private TextField cuenta;
    private TextField tarjeta;
    private TextField retenido;
    private TextField cantidad;
    private TextField tipo;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Movimiento> binder;

    private Movimiento movimiento;

    private final MovimientoService movimientoService;

    @Autowired
    public MovimientosView(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
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
        grid.addColumn("cantidad").setAutoWidth(true);
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

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.movimiento == null) {
                    this.movimiento = new Movimiento();
                }
                binder.writeBean(this.movimiento);
                movimientoService.update(this.movimiento);
                clearForm();
                refreshGrid();
                Notification.show("Movimiento details stored.");
                UI.getCurrent().navigate(MovimientosView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the movimiento details.");
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
                // when a row is selected but the data is no longer available,
                // refresh grid
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
        cuenta = new TextField("Cuenta");
        tarjeta = new TextField("Tarjeta");
        retenido = new TextField("Retenido");
        cantidad = new TextField("Cantidad");
        tipo = new TextField("Tipo");
        formLayout.add(cuenta, tarjeta, retenido, cantidad, tipo);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
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
