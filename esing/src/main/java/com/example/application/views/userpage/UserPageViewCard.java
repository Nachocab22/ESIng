package com.example.application.views.userpage;
import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;

public class UserPageViewCard extends ListItem{

    public UserPageViewCard(String text, Cuenta cuentas, Tarjeta tarjetas) {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");

        /*
        Div div = new Div();
        div.addClassNames("bg-contrast", "flex items-center", "justify-center", "mb-m", "overflow-hidden",
                "rounded-m w-full");
        div.setHeight("160px");
        */

        Span header = new Span();
        header.addClassNames("text-xl", "font-semibold");
        header.setText(text);

        Span table = new Span();
        table.addClassNames("text-s", "text-secondary");
        if(cuentas != null) {
            Grid<Cuenta> gridAccounts = new Grid<>(Cuenta.class, false);
            //ADD CUENTAs TO GRID
            table.add(new Grid<Cuenta>());
        }
        if(tarjetas != null) {
            Grid<Tarjeta> gridAccounts = new Grid<>(Tarjeta.class, false);
            //ADD TARJETAs TO GRID
            table.add(new Grid<Tarjeta>());
        }

        add(header, table);

    }
}