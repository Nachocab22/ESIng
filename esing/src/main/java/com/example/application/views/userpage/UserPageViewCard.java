package com.example.application.views.userpage;
import java.util.List;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;

public class UserPageViewCard extends ListItem{

    public UserPageViewCard(String text, List<Cuenta> cuentas, List<Tarjeta> tarjetas) {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");

        Span header = new Span();
        header.addClassNames("text-xl", "font-semibold");
        header.setText(text);

        Span table = new Span();
        table.addClassNames("text-s", "text-secondary");

        add(header, table);

    }
}