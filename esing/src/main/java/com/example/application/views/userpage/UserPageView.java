package com.example.application.views.userpage;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.service.CuentaService;
import com.example.application.data.service.TarjetaService;
import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

//CHANGE PERMISSIONS (ANONYMOUS ARE NOT ALLOWED, THEY ARE ALLOWED BEFORE ADDING ACCOUNTS AND CARDS)

@PageTitle("Tu zona")
@Route(value = "user-page", layout = MainLayout.class)
@RolesAllowed("USER")
public class UserPageView extends Main implements HasComponents, HasStyle {

    private OrderedList dataContainer;
    Grid<Cuenta> gridCuentas = new Grid<>(Cuenta.class, false);
    Grid<Tarjeta> gridTarjetas = new Grid<>(Tarjeta.class, false);

    public UserPageView(CuentaService cuentaService, TarjetaService tarjetaService, UserService userService) {
        constructUI();

        dataContainer.add(new UserPageViewCard("Sus cuentas", null, null));
        dataContainer.add(new H1());
        gridCuentas.addColumn("iban").setHeader("Numero de cuenta");
        gridCuentas.addColumn("saldo").setHeader("Saldo disponible");
        gridCuentas.addColumn("mote").setHeader("Nombre de la cuenta");
        gridCuentas.setItems(cuentaService.findAllCuentas(userService.getCurrentUser().getId().toString()));
        gridCuentas.setAllRowsVisible(isVisible());
        gridCuentas.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        dataContainer.add(gridCuentas);
        dataContainer.add(new UserPageViewCard("Sus tarjetas", null, null));
        dataContainer.add(new H1());
        gridTarjetas.addColumn("numero").setHeader("Numero de tarjeta");
        gridTarjetas.addColumn("caducidad").setHeader("Fecha de caducidad");
        gridTarjetas.addColumn(cuenta -> cuenta.getCuenta().getMote());
        gridTarjetas.setItems(tarjetaService.findAllTarjetas(userService.getCurrentUser().getId().toString()));
        gridTarjetas.setAllRowsVisible(isVisible());
        gridTarjetas.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        dataContainer.add(gridTarjetas);
    }

    private void constructUI() {
        addClassNames("e-s-ing-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("items-center", "justify-between");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Sus detalles");
        H2 br = new H2();
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph description = new Paragraph("Tu zona de cliente");
        description.addClassNames("mb-xl", "mt-0", "text-secondary");
        headerContainer.add(header, description);


        dataContainer = new OrderedList();
        dataContainer.addClassNames("gap-m", "list-none", "m-0", "p-0");

        container.add(header, br);
        add(container, dataContainer);

    }
}