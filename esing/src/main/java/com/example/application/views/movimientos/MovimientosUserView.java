package com.example.application.views.movimientos;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.example.application.data.entity.Movimiento;
import com.example.application.data.service.MovimientoService;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@PageTitle("Mis Movimientos")
@Route(value = "movimientosUser", layout = MainLayout.class)
@RolesAllowed("USER")
public class MovimientosUserView extends Div{
    
//    private final BeanValidationBinder<Movimiento> binder;
    
    Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);
    
    private Movimiento movimiento;
	private final MovimientoService service;
	
	@Autowired
	public MovimientosUserView(MovimientoService service, UserService userService) {
		this.service = service;
		addClassName("movimiento-user-view");
		
		add(createTitle());
		add(createGridLayout());
		
		grid.addColumn(cuenta -> cuenta.getCuenta().getIban()).setAutoWidth(true);
        grid.addColumn("tarjeta").setAutoWidth(true);
        grid.addColumn("retenido").setAutoWidth(true);
        grid.addColumn("concepto").setAutoWidth(true);
        grid.addColumn("cantidad").setAutoWidth(true);
        grid.addColumn("fecha_op").setAutoWidth(true);
        grid.addColumn("tipo").setAutoWidth(true);
        grid.setItems(service.findAllMovimientos(userService.getCurrentUser().getId().toString()));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
	}

    private Component createGridLayout() {
    	HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setClassName("grid-layout");
        gridLayout.add(grid);
        return gridLayout;
     }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

//    private void clearForm() {
//        populateForm(null);
//    }

//    private void populateForm(Movimiento value) {
//        this.movimiento = value;
//        binder.readBean(this.movimiento);
//
//    }
	
	private Component createTitle() {
		return new H3("Lista de movimientos");
	}
}