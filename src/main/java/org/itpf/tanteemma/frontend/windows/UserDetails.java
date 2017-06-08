package org.itpf.tanteemma.frontend.windows;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import org.itpf.tanteemma.backend.models.Person;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class UserDetails extends Window {

    public UserDetails() {

        super("User Details");

        center();
        setClosable(true);
        GridLayout layout = new GridLayout(10,5);
        layout.setMargin(true);
        setContent(layout);

        TextField vorname = new TextField();
        layout.addComponent(new Label("Vorname"));
        layout.addComponent(vorname);

        TextField nachname = new TextField();
        layout.addComponent(new Label("Nachname"));
        layout.addComponent(nachname);


        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.setItems("Männlich", "Weiblich");
        layout.addComponent(new Label("Geschlecht"));
        layout.addComponent(gender);



        TextField telefon = new TextField();
        layout.addComponent(new Label("Telefon"));
        layout.addComponent(telefon);


        TextField email = new TextField();
        layout.addComponent(new Label("E-Mail"));
        layout.addComponent(email);


        RadioButtonGroup<String> role = new RadioButtonGroup<>();
        role.setItems("Kunde", "Mitarbeiter", "Admin");
        layout.addComponent(new Label("Rolle"));
        layout.addComponent(role);

        TextField strasse = new TextField();
        layout.addComponent(new Label("Straße"));
        layout.addComponent(strasse);

        TextField ort = new TextField();
        layout.addComponent(new Label("Ort"));
        layout.addComponent(ort);


        TextField plz = new TextField();
        layout.addComponent(new Label("Postleitzahl"));
        layout.addComponent(plz);

        Button ok = new Button("Speichern");
        Button cancel = new Button("Abbrechen");
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                insertPerson(new Person());
            }
        });

    }

    private void insertPerson(Person p){


    }



}
