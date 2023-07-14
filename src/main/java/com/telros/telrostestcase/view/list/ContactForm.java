package com.telros.telrostestcase.view.list;

import com.telros.telrostestcase.model.User;
import com.telros.telrostestcase.model.role.Role;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ContactForm extends FormLayout {
    private User user;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField surname = new TextField("Surname name");
    TextField email = new TextField("Email");
    TextField phone = new TextField("Phone");
    TextField birthday = new TextField("Birthday");
    ComboBox<Role> roleComboBox = new ComboBox<>("Role");
    Binder<User> binder = new BeanValidationBinder<>(User.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ContactForm(List<Role> roles) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);

        roleComboBox.setItems(roles);
//        roleComboBox.setItemLabelGenerator(;);
        add((Component) firstName,
                lastName,
                surname,
                email,
                phone,
                birthday,
                roleComboBox);
//                createButtonsLayout());
    }

//    private HorizontalLayout createButtonsLayout() {
//        user.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//
//        user.addClickShortcut(Key.ENTER);
//        close.addClickShortcut(Key.ESCAPE);
//
//        user.addClickListener(event -> validateAndSave());
//        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));
//        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
//
//
//        binder.addStatusChangeListener(e -> user.setEnabled(binder.isValid()));
//
//        return new HorizontalLayout(user, delete, close);
//    }

    public void setContact(User user) {
        this.user = user;
        binder.readBean(user);
    }


    private void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private User user;


        protected ContactFormEvent(ContactForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getContact() {
            return user;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, User contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, User user) {
            super(source, user);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}