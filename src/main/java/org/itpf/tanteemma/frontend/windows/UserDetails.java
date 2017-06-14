package org.itpf.tanteemma.frontend.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.itpf.tanteemma.backend.models.Person;
import org.itpf.tanteemma.backend.services.PersonService;
import org.itpf.tanteemma.frontend.EntryPoint;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class UserDetails extends Window {

    private boolean edit = false;

    public UserDetails(final Person p) {

        super("User Details");

        if (p != null) {
            edit = true;
        }

        center();
        setClosable(true);
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        setContent(layout);

        TextField vorname = new TextField("Vorname");
        vorname.setValue(p == null ? "" : p.getV_vorname());
        layout.addComponent(vorname);

        TextField nachname = new TextField("Nachname");
        nachname.setValue(p == null ? "" : p.getV_name());
        layout.addComponent(nachname);


        RadioButtonGroup<String> gender = new RadioButtonGroup<>("Geschlecht");
        gender.setItems(Person.MEN, Person.FEM);
        gender.setSelectedItem(p == null ? Person.MEN : p.getV_geschlecht());
        layout.addComponent(gender);


        TextField telefon = new TextField("Telefon");
        telefon.setValue(p == null ? "" : p.getV_telefon());
        layout.addComponent(telefon);


        TextField email = new TextField("E-Mail");
        email.setValue(p == null ? "" : p.getV_telefon());
        layout.addComponent(email);


        RadioButtonGroup<String> role = new RadioButtonGroup<>("Rolle");
        role.setItems(Person.KUNDE, Person.WORKER);
        role.setSelectedItem(p == null ? Person.KUNDE : p.getV_rolle());
        layout.addComponent(role);

        TextField strasse = new TextField("Straße");
        strasse.setValue(p == null ? "" : p.getV_strasse());
        layout.addComponent(strasse);

        TextField ort = new TextField("Ort");
        ort.setValue(p == null ? "" : p.getV_ort());
        layout.addComponent(ort);


        TextField plz = new TextField("Postleitzahl");
        plz.setValue(p == null ? "" : p.getV_ort());
        layout.addComponent(plz);

        HorizontalLayout hlayout = new HorizontalLayout();

        layout.addComponent(hlayout);

        Button ok = new Button("Speichern");
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                Person tmpP = new Person(0, nachname.getValue(), vorname.getValue(), gender.getValue(),
                                         telefon.getValue(), email.getValue(), role.getValue(), strasse.getValue(),
                                         ort.getValue(), plz.getValue());
                if (!edit) {
                    EntryPoint.users.addUser(tmpP);

                    PersonService.getInstance().createNewPerson(tmpP);
                    Notification.show("Person " + tmpP.getV_vorname() + " " + tmpP.getV_name() + " wurde erstellt.");

                } else {
                    tmpP.setPn_id(p.getPn_id());
                    EntryPoint.users.removeUser(p);
                    EntryPoint.users.addUser(tmpP);
                    PersonService.getInstance().updatePerson(tmpP);
                }

                close();
            }
        });


        hlayout.addComponent(ok);

    }


}
