package ru.sergalas.admin.web.controller;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import ru.sergalas.admin.entity.Periodicity;
import ru.sergalas.admin.entity.User;
import ru.sergalas.admin.services.RegisterService;
import ru.sergalas.admin.services.UserService;
import ru.sergalas.admin.web.layout.AdminLayout;

import java.util.ArrayList;
import java.util.List;

@Route(value = "users", layout = AdminLayout.class)
@PageTitle("Пользователи")
@RequiredArgsConstructor
public class UserView extends VerticalLayout {

    private final UserService userService;
    private final RegisterService registerService;

    private Grid<User> grid;
    private TextField filterText;


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Инициализация UI
        grid = new Grid<>(User.class);
        filterText = new TextField("Поиск");

        setSizeFull();
        configureGrid();
        add(getToolbar(), grid);
        updateList();

        grid.asSingleSelect().addValueChangeListener(event -> {
            User user = event.getValue();
            if (user != null) {
                edit(user);
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
        grid.addColumn(User::id).setHeader("ID").setAutoWidth(true);
        grid.addColumn(User::username).setHeader("Username").setAutoWidth(true);
        grid.addColumn(User::email).setHeader("Email").setAutoWidth(true);
        grid.addColumn(User::firstName).setHeader("Имя").setAutoWidth(true);
        grid.addColumn(User::lastName).setHeader("Фамилия").setAutoWidth(true);
        grid.addColumn(User::password).setHeader("Пароль").setAutoWidth(true);
        grid.addColumn(User::role).setHeader("Роли ").setAutoWidth(true);


        grid.addComponentColumn(user -> {
            Button deleteBtn = new Button("Удалить");
            deleteBtn.addClickListener(e -> deletePeriodicity(user));
            deleteBtn.addClassName("delete-btn");
            return deleteBtn;
        }).setHeader("Действия").setAutoWidth(true);

        grid.setAllRowsVisible(true);
    }

    private void updateList() {
        String data = filterText.getValue();
        List<User> users = userService.getIndex(null,null,null);
        grid.setItems(users);
    }

    private void add() {
        edit(new User(
                null,
                "",//username
                "", // email
                "", // firstName
                "",// lastName
                "",//password
                List.of()//roles
        ));
    }

    private void edit(User user) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(user.id() == null ? "Новый срок" : "Редактировать срок");

        FormLayout form = new FormLayout();

        TextField id = new TextField("Chat ID");
        TextField username = new TextField("Username");
        TextField email = new TextField("Email");
        TextField firstName = new TextField("Имя");
        TextField lastName = new TextField("Фамилия");
        PasswordField password = new PasswordField("Пароль");
        ComboBox<String> role = new ComboBox<>("Роль");

        id.setValue(user.id() != null ? user.id() : "");;
        username.setValue(user.username());
        email.setValue(user.email());
        firstName.setValue(user.firstName());
        lastName.setValue(user.lastName());
        password.setValue(user.password() != null ? user.password() : "");
        List<String> roles = userService.getRoles();
        role.setItems(roles);
        if (user.role() != null && !user.role().isEmpty()) {
            user.role().forEach(role::setValue);
        }


        form.add(id, username, email, firstName, lastName, password, role);
        id.setReadOnly(user.id() != null);

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Сохранить", e -> {
            try {
                String selectedRole = role.getValue();
                List<String> updatedRoles = new ArrayList<>(user.role());
                if (selectedRole != null && !selectedRole.isEmpty() && !updatedRoles.contains(selectedRole)) {
                    updatedRoles.add(selectedRole);
                }

                User updatedUser = new User(
                        id.getValue(),
                        username.getValue(),
                        email.getValue(),
                        firstName.getValue(),
                        lastName.getValue(),
                        password.getValue(),
                        updatedRoles
                );

                if (user.id() == null) {
                    registerService.create(updatedUser);
                } else {
                   registerService.update(updatedUser);
                }
                updateList();
                dialog.close();
                Notification.show("Админ сохранён");
            } catch (Exception ex) {
                Notification.show("Ошибка: " + ex.getMessage());
            }
        });

        Button cancelButton = new Button("Отмена", e -> dialog.close());

        buttons.add(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void deletePeriodicity(User user) {
        try {
            registerService.delete(user.id());
            updateList();
            Notification.show("Срок удалён");
        } catch (Exception e) {
            Notification.show("Ошибка при удалении: " + e.getMessage());
        }
    }
}
