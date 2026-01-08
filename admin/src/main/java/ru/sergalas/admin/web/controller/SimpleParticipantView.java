package ru.sergalas.admin.web.controller;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.sergalas.admin.web.layout.AdminLayout;

@Route(value = "simple-participants", layout = AdminLayout.class)
@PageTitle("Просто Участники")
public class SimpleParticipantView extends VerticalLayout {

    public SimpleParticipantView() {
        add(new H2("Это простая страница участников"));
        setSizeFull();
    }
}
