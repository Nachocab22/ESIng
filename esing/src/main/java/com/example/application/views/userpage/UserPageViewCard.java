package com.example.application.views.userpage;

import com.example.application.data.entity.Tarjeta;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;

public class UserPageViewCard extends ListItem{

    public UserPageViewCard(String text) {
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
        //Grid<Tarjeta> gridAccounts = new Grid<>(Tarjeta.class, false);

        //table.add(new Grid<Tarjeta>());

        Span subtitle = new Span();
        subtitle.addClassNames("text-s", "text-secondary");
        subtitle.setText("Card subtitle");

        Paragraph description = new Paragraph(
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.");
        description.addClassName("my-m");

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Label");

        add(header, subtitle, description, badge);

    }
}
