package ru.sergalas.admin.web.controller;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import ru.sergalas.admin.entity.Participant;
import ru.sergalas.admin.entity.Periodicity;
import ru.sergalas.admin.services.PeriodicityService;
import ru.sergalas.admin.web.layout.AdminLayout;

import java.util.List;


@Route(value = "periodicity", layout = AdminLayout.class)
@PageTitle("Сроки")
@RequiredArgsConstructor
public class PeriodicityView extends VerticalLayout {
    private final PeriodicityService periodicityService;

    private Grid<Periodicity> grid;
    private TextField filterText;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Инициализация UI
        grid = new Grid<>(Periodicity.class);
        filterText = new TextField("Поиск");

        setSizeFull();
        configureGrid();
        add(getToolbar(), grid);
        updateList();

        grid.asSingleSelect().addValueChangeListener(event -> {
            Periodicity periodicity = event.getValue();
            if (periodicity != null) {
                edit(periodicity);
            }
        });
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Фильтр...");
        filterText.setClearButtonVisible(true);
        filterText.addValueChangeListener(e -> updateList());

        Button addParticipantButton = new Button("Добавить участника");
        addParticipantButton.addClickListener(e -> add());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addParticipantButton);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        return toolbar;
    }

    private void configureGrid() {
        grid.addColumn(Periodicity::id).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Periodicity::chatId).setHeader("Chat ID").setAutoWidth(true);
        grid.addColumn(Periodicity::title).setHeader("Заголовок").setAutoWidth(true);
        grid.addColumn(Periodicity::description).setHeader("Описание").setAutoWidth(true);
        grid.addColumn(Periodicity::date).setHeader("Дата").setAutoWidth(true);

        grid.addComponentColumn(participant -> {
            Button deleteBtn = new Button("Удалить");
            deleteBtn.addClickListener(e -> deletePeriodicity(participant));
            deleteBtn.addClassName("delete-btn");
            return deleteBtn;
        }).setHeader("Действия").setAutoWidth(true);

        grid.setAllRowsVisible(true);
    }

    private void updateList() {
        String data = filterText.getValue();
        List<Periodicity> periodicity = periodicityService.getAllPeriodicity(data);
        grid.setItems(periodicity);
    }

    private void add() {
        edit(new Periodicity(
                null,
                "",//chatId
                "", // title
                "", // description
                ""// date
        ));
    }

    private void edit(Periodicity periodicity) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(periodicity.id() == null ? "Новый срок" : "Редактировать срок");

        FormLayout form = new FormLayout();
        TextField chatId = new TextField("Chat ID");
        TextField title = new TextField("Заголовок");
        TextField description = new TextField("Описание");
        TextField date = new TextField("Дата (ДД.ММ)");

        chatId.setValue(periodicity.chatId());
        title.setValue(periodicity.title());
        description.setValue(periodicity.description());
        date.setValue(periodicity.date());

        form.add(chatId, title, description,  date);

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Сохранить", e -> {
            try {
                // Собираем данные из формы в новый record
                Periodicity updatedPeriodicity = new Periodicity(
                        periodicity.id(),
                        chatId.getValue(),
                        title.getValue(),
                        description.getValue(),
                        date.getValue()
                );

                if (periodicity.id() == null) {
                    periodicityService.createPeriodicity(updatedPeriodicity);
                } else {
                    periodicityService.updatePeriodicity(updatedPeriodicity);
                }
                updateList();
                dialog.close();
                Notification.show("Срок сохранён");
            } catch (Exception ex) {
                Notification.show("Ошибка: " + ex.getMessage());
            }
        });

        Button cancelButton = new Button("Отмена", e -> dialog.close());

        buttons.add(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void deletePeriodicity(Periodicity periodicity) {
        try {
            periodicityService.deletePeriodicity(periodicity);
            updateList();
            Notification.show("Срок удалён");
        } catch (Exception e) {
            Notification.show("Ошибка при удалении: " + e.getMessage());
        }
    }
}
