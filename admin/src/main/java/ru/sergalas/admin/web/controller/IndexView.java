package ru.sergalas.admin.web.controller;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.sergalas.admin.web.layout.AdminLayout;

@Route(value = "", layout = AdminLayout.class)
@PageTitle("Главная")
public class IndexView  extends VerticalLayout {
    public IndexView() {
        add(new H2("Добро пожаловать в админку!"));
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
