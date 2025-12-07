package ru.sergalas.admin.web.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.Theme;
import ru.sergalas.admin.web.controller.ParticipantView;
import ru.sergalas.admin.web.controller.PeriodicityView;
import ru.sergalas.admin.web.controller.UserView;

public class AdminLayout extends AppLayout implements AfterNavigationObserver {

    private final Button usersBtn;
    private final Button ordersBtn;
    private final Button analyticsBtn;

    public AdminLayout() {
        H1 title = new H1("Админка");
        title.getStyle()
                .set("font-size", "1.25rem")
                .set("margin", "0")
                .set("padding-left", "var(--lumo-space-m)");

        HorizontalLayout header = new HorizontalLayout(title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();
        header.getStyle().set("box-shadow", "0 2px 4px -1px rgba(0,0,0,0.1)");
        addToNavbar(true, header);

        usersBtn = createMenuButton("Пользователи", VaadinIcon.USERS, ParticipantView.class);
        ordersBtn = createMenuButton("Заказы", VaadinIcon.LIST, PeriodicityView.class);
        analyticsBtn = createMenuButton("Аналитика", VaadinIcon.CHART, UserView.class);

        VerticalLayout drawerContent = new VerticalLayout(
                usersBtn,
                ordersBtn,
                analyticsBtn
        );
        drawerContent.setPadding(false);
        drawerContent.setSpacing(false);
        drawerContent.getStyle().set("width", "100%");

        addToDrawer(usersBtn, ordersBtn, analyticsBtn);
    }


    private Button createMenuButton(String label, VaadinIcon iconType, Class<? extends Component>  target) {
        String href = RouteConfiguration.forApplicationScope()
                .getUrl(target);

        if (href == null) {
            throw new IllegalStateException("Route not found for class: " + target.getName());
        }

        RouterLink link = new RouterLink();
        link.setRoute(target, null);
        Icon icon = iconType.create();
        icon.getStyle().set("margin-right", "var(--lumo-space-s)");
        Span text = new Span(label);

        link.getElement().appendChild(icon.getElement(), text.getElement());

        Button button = new Button();
        button.getStyle()
                .set("width", "100%")
                .set("justify-content", "flex-start")
                .set("text-align", "left")
                .set("padding", "var(--lumo-space-m)")
                .set("background", "transparent")
                .set("border", "none")
                .set("font-weight", "500")
                .set("color", "inherit");
        button.addClassName("menu-item");

        button.getElement().appendChild(link.getElement());

        return button;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        usersBtn.removeClassName("active");
        ordersBtn.removeClassName("active");
        analyticsBtn.removeClassName("active");
        String path = event.getLocation().getPath();
        if (path.startsWith("users")) {
            usersBtn.addClassName("active");
        } else if (path.startsWith("orders")) {
            ordersBtn.addClassName("active");
        } else if (path.startsWith("analytics")) {
            analyticsBtn.addClassName("active");
        }
    }
}
