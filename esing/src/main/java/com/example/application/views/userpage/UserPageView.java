package com.example.application.views.userpage;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

//CHANGE PERMISSIONS (ANONYMOUS ARE NOT ALLOWED, THEY ARE ALLOWED BEFORE ADDING ACCOUNTS AND CARDS)

@PageTitle("ESIng")
@Route(value = "user-page", layout = MainLayout.class)
@RolesAllowed("USER")
public class UserPageView extends Main implements HasComponents, HasStyle {

    private OrderedList dataContainer;

    public UserPageView() {
        constructUI();

        //Add user accounts and user cards
        dataContainer.add(new UserPageViewCard("Sus cuentas", null, null));
        dataContainer.add(new H1());
        dataContainer.add(new UserPageViewCard("Sus tarjetas", null, null));
        dataContainer.add(new H1());
    }

    private void constructUI() {
        addClassNames("e-s-ing-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("items-center", "justify-between");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Sus detalles");
        H2 br = new H2();
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph description = new Paragraph("Royalty free photos and pictures, courtesy of Unsplash");
        description.addClassNames("mb-xl", "mt-0", "text-secondary");
        headerContainer.add(header, description);


        dataContainer = new OrderedList();
        dataContainer.addClassNames("gap-m", "list-none", "m-0", "p-0");

        container.add(header, br);
        add(container, dataContainer);

    }
}