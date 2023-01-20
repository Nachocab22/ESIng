package com.example.application.views.movimientos;

import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.User;
import com.example.application.data.service.MovimientoService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
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

@PageTitle("Mis Movimientos")
@Route(value = "movimientosUser", layout = MainLayout.class)
@RolesAllowed("USER")
public class MovimientosUserView extends Div implements BeforeEnterObserver {

	private final String MOVIMIENTO_ID = "movimientoID";
    private final String MOVIMIENTO_EDIT_ROUTE_TEMPLATE = "movimientos/%s/edit";

    private final Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);
    private AuthenticatedUser authenticatedUser;
    
    private final BeanValidationBinder<Movimiento> binder;

    private Movimiento movimiento;

    private final MovimientoService movimientoService;
    
    @Autowired
    public MovimientosUserView(MovimientoService movimientoService, AuthenticatedUser authenticatedUser) {
        this.movimientoService = movimientoService;
        this.authenticatedUser = authenticatedUser;
        addClassNames("movimientos-view");
        
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
        	
        User user = maybeUser.get();
        Avatar name = new Avatar(user.getName());
        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("cuenta").setAutoWidth(true);
        grid.addColumn("tarjeta").setAutoWidth(true);
        grid.addColumn("retenido").setAutoWidth(true);
        grid.addColumn("concepto").setAutoWidth(true);
        grid.addColumn("cantidad").setAutoWidth(true);
        grid.addColumn("fecha_op").setAutoWidth(true);
        grid.addColumn("tipo").setAutoWidth(true);
        grid.setItems(movimientoService.findAllMovimientos(name.getName()));
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
        }
        // Configure Form
        binder = new BeanValidationBinder<>(Movimiento.class);   
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
