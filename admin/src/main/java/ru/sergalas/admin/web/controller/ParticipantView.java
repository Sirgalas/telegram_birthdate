package ru.sergalas.admin.web.controller;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import ru.sergalas.admin.entity.Participant;
import ru.sergalas.admin.services.ParticipantService;
import ru.sergalas.admin.web.layout.AdminLayout;

import java.util.List;
import java.util.UUID;

@Route(value = "participants", layout = AdminLayout.class)
@PageTitle("Участники")
@RequiredArgsConstructor
public class ParticipantView extends VerticalLayout {
    private final ParticipantService participantService;

    private Grid<Participant> grid;
    private TextField filterText;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Инициализация UI
        grid = new Grid<>(Participant.class);
        filterText = new TextField("Поиск");

        setSizeFull();
        configureGrid();
        add(getToolbar(), grid);
        updateList();

        grid.asSingleSelect().addValueChangeListener(event -> {
            Participant participant = event.getValue();
            if (participant != null) {
                editParticipant(participant);
            }
        });
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Фильтр...");
        filterText.setClearButtonVisible(true);
        filterText.addValueChangeListener(e -> updateList());

        Button addParticipantButton = new Button("Добавить участника");
        addParticipantButton.addClickListener(e -> addParticipant());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addParticipantButton);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        return toolbar;
    }

    private void configureGrid() {
        grid.addColumn(Participant::id).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Participant::chatId).setHeader("Chat ID").setAutoWidth(true);
        grid.addColumn(Participant::firstName).setHeader("Имя").setAutoWidth(true);
        grid.addColumn(Participant::lastName).setHeader("Фамилия").setAutoWidth(true);
        grid.addColumn(Participant::patronymic).setHeader("Отчество").setAutoWidth(true);
        grid.addColumn(Participant::date).setHeader("Дата").setAutoWidth(true);

        grid.addComponentColumn(participant -> {
            Button deleteBtn = new Button("Удалить");
            deleteBtn.addClickListener(e -> deleteParticipant(participant));
            deleteBtn.addClassName("delete-btn");
            return deleteBtn;
        }).setHeader("Действия").setAutoWidth(true);

        grid.setAllRowsVisible(true);
    }

    private void updateList() {
        String data = filterText.getValue();
        List<Participant> participants = participantService.getAllParticipants(data);
        grid.setItems(participants);
    }

    private void addParticipant() {
        editParticipant(new Participant(
                null,
                "", // chatId
                "", // firstName
                "", // lastName
                "", // patronymic
                ""  // date
        ));
    }

    private void editParticipant(Participant participant) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(participant.id() == null ? "Новый участник" : "Редактировать участника");

        FormLayout form = new FormLayout();
        TextField chatId = new TextField("Chat ID");
        TextField firstName = new TextField("Имя");
        TextField lastName = new TextField("Фамилия");
        TextField patronymic = new TextField("Отчество");
        TextField date = new TextField("Дата (ДД.ММ)");

        // Заполняем поля из record
        chatId.setValue(participant.chatId());
        firstName.setValue(participant.firstName());
        lastName.setValue(participant.lastName());
        patronymic.setValue(participant.patronymic());
        date.setValue(participant.date());

        form.add(chatId, firstName, lastName, patronymic, date);

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Сохранить", e -> {
            try {
                // Собираем данные из формы в новый record
                Participant updatedParticipant = new Participant(
                        participant.id(), // оставляем старый ID
                        chatId.getValue(),
                        firstName.getValue(),
                        lastName.getValue(),
                        patronymic.getValue(),
                        date.getValue()
                );

                if (participant.id() == null) {
                    participantService.createParticipant(updatedParticipant);
                } else {
                    participantService.updateParticipant(updatedParticipant);
                }
                updateList();
                dialog.close();
                Notification.show("Участник сохранён");
            } catch (Exception ex) {
                Notification.show("Ошибка: " + ex.getMessage());
            }
        });

        Button cancelButton = new Button("Отмена", e -> dialog.close());

        buttons.add(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void deleteParticipant(Participant participant) {
        try {
            participantService.deleteParticipant(participant);
            updateList();
            Notification.show("Участник удалён");
        } catch (Exception e) {
            Notification.show("Ошибка при удалении: " + e.getMessage());
        }
    }
}
