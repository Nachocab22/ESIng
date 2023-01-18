//Commit 9 Jan
package com.example.application.views;

import com.example.application.components.appnav.AppNav;
import com.example.application.components.appnav.AppNavItem;
import com.example.application.data.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.esing.ESIngView;
import com.example.application.views.incidencia.IncidenciaAdminView;
import com.example.application.views.incidencia.IncidenciaView;
import com.example.application.views.introducetarjetadecredito.IntroducetarjetadecreditoView;
import com.example.application.views.pago.PagoView;
import com.example.application.views.userpage.UserPageView;
import com.example.application.views.movimientos.MovimientosView;
import com.example.application.views.registro.RegistroView;
import com.example.application.views.usuarios.UsuariosView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("ESIng");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();
        
        if (accessChecker.hasAccess(ESIngView.class)) {
            nav.addItem(new AppNavItem("ESIng", ESIngView.class, "la la-scroll"));

        }

        if (accessChecker.hasAccess(RegistroView.class)) {
            nav.addItem(new AppNavItem("Registro", RegistroView.class, "la la-user"));

        }
        
        if (accessChecker.hasAccess(UserPageView.class)) {
            nav.addItem(new AppNavItem("Dashboard", UserPageView.class, "la la-user"));
            
        }
        
        if (accessChecker.hasAccess(IntroducetarjetadecreditoView.class)) {
            nav.addItem(new AppNavItem("Introduce tarjeta de credito", IntroducetarjetadecreditoView.class, "la la-credit-card"));
            
        }
           
        if (accessChecker.hasAccess(MovimientosView.class)) {
        	nav.addItem(new AppNavItem("Movimientos", MovimientosView.class, "la la-colums"));
        }

        if (accessChecker.hasAccess(PagoView.class)) {
            nav.addItem(new AppNavItem("Pago", PagoView.class, "la la-credit-card"));

        }
        if (accessChecker.hasAccess(UsuariosView.class)) {
            nav.addItem(new AppNavItem("Usuarios", UsuariosView.class, "la la-columns"));

        }
        
        if (accessChecker.hasAccess(IncidenciaView.class)) {
        	nav.addItem(new AppNavItem("Contacte con su gestor", IncidenciaView.class, "la la-incidence"));
        }
        
        if (accessChecker.hasAccess(IncidenciaAdminView.class)) {
        	nav.addItem(new AppNavItem("Lista de incidencias" , IncidenciaAdminView.class, "la la-incidence-admin"));
        }
        

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
