package org.itpf.tanteemma.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.itpf.tanteemma.backend.models.Person;
import org.itpf.tanteemma.backend.services.PersonService;
import org.itpf.tanteemma.frontend.EntryPoint;

/**
 * Created by Lindner.Patrick on 13.06.2017.
 */
public class SignInView extends Panel implements View {


    public static final String VIEW_NAME = "";

    public SignInView() {

        super("Login");
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        TextField firstname = new TextField("Vorname");
        firstname.setIcon(FontAwesome.USER);
        layout.addComponent(firstname);

        TextField lastname = new TextField("Nachname");
        lastname.setIcon(FontAwesome.USER);
        layout.addComponent(lastname);

        Button login = new Button("Login");
        layout.addComponent(login);
        login.addClickListener(evt -> {
            Person p = PersonService.getInstance().findPersonByName(firstname.getValue(), lastname.getValue());
//            Person p = new Person(1, "patrick", "lindner", "m", "123451111", "p@me.com", Person.KUNDE, "Straße 1", "ort 1",
//                                  "plz1");
            if (p != null) {
                EntryPoint.person = p;
                EntryPoint.navigator.navigateTo(Products.VIEW_NAME);
            } else {
                Notification.show("Dieser User existiert nicht");
            }

        });

        setWidth(null);


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
