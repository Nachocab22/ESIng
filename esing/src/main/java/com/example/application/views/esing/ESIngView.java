package com.example.application.views.esing;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("ESIng")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class ESIngView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;

    public ESIngView() {
        constructUI();

        imageContainer.add(new ESIngViewCard("Transacciones rapidas y sencillas online",
                "https://leonikapiel.com/wp-content/uploads/2021/02/monedero-artesanal-de-piel-cartera-de-piel-azul-colores-leonikapiel-monedero-artesano-de-cuero-de-moda-para-mujer-leonika-piel-4-2.jpg"));
        imageContainer.add(new ESIngViewCard("La seguridad de un banco tradicional a tu alcance",
                "https://d500.epimg.net/cincodias/imagenes/2019/11/29/companias/1575024209_206984_1576845228_noticia_normal.jpg"));
        imageContainer.add(new ESIngViewCard("Comprueba todos tus movimientos en cualquier momento",
                "https://www.claseejecutiva.uc.cl/wp-content/uploads/2021/11/estado-de-resultados-que-es-el-estado-de-resultado-w-min-thegem-blog-timeline-large.jpg"));
        imageContainer.add(new ESIngViewCard("Asistencia personalizada con nuestros operadores",
                "https://criteriosdigital.com/wp-content/uploads/2021/06/WAVEBREAKMEDIA-FREEPIK.jpg"));
        imageContainer.add(new ESIngViewCard("Cree su cuenta totalmente gratis",
                "https://www.enter.co/wp-content/uploads/2018/07/john-schnobrich-520023-unsplash-1024x768.jpg"));
        imageContainer.add(new ESIngViewCard("Administra tu dinero y planea tus ahorros",
                "https://d500.epimg.net/cincodias/imagenes/2021/01/11/extras/1610380859_885429_1610382172_noticia_normal.jpg"));

    }

    private void constructUI() {
        addClassNames("e-s-ing-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("items-center", "justify-between");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("ESIng");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph description = new Paragraph("Imagenes de stock");
        description.addClassNames("mb-xl", "mt-0", "text-secondary");
        headerContainer.add(header, description);

        imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");

        container.add(header);
        add(container, imageContainer);

    }
}