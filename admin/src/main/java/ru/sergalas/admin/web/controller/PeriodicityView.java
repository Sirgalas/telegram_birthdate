package ru.sergalas.admin.web.controller;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.sergalas.admin.web.layout.AdminLayout;

@Route(value = "periodicity", layout = AdminLayout.class)
@PageTitle("Пользователи")
public class PeriodicityView extends VerticalLayout {
}
