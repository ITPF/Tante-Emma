package org.itpf.tanteemma.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.itpf.tanteemma.backend.models.Person;
import org.itpf.tanteemma.frontend.customobjects.NavigationBar;
import org.itpf.tanteemma.frontend.windows.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class Users extends Panel implements View {

    public static final String VIEW_NAME = "users";

    private Grid<Person> grid;

    private List<Person> persons;

    public Users() {
        super("Userverwaltung");
        persons = new ArrayList<>();
// Arrays.asList(
//
//                new Person[]{
//                        new Person(1, "patrick", "lindner", "m", "123451111", "p@me.com", "rolle1", "Straße 1", "ort 1",
//                                   "plz1"),
//                        new Person(2, "tatjana", "kraemer", "w", "123451111", "t@me.com", "rolle2", "Straße 1", "ort 2",
//                                   "plz1"),
//                        new Person(3, "igor", "stoljarow", "m", "123451111", "i@me.com", "rolle3", "Straße 1", "ort 3",
//                                   "plz1"),
//                        new Person(4, "filiz", "neumann", "w", "123451111", "f@me.com", "rolle4", "Straße 1", "ort 4",
//                                   "plz1")
//
//
//                });

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        layout.addComponent(new NavigationBar());


        grid = new Grid<>();

        layout.addComponent(grid);
        grid.addColumn(Person::getV_name).setCaption("Name");
        grid.addColumn(Person::getV_vorname).setCaption("Vorname");
        grid.addColumn(Person::getV_email).setCaption("E-Mail");
        grid.addColumn(Person::getV_plz).setCaption("Postleitzahl");
        grid.setItems(persons);

        grid.setSizeFull();
        setWidth(50, Unit.PERCENTAGE);

        Button addUser = new Button("Add User");
        addUser.addClickListener((Button.ClickListener) clickEvent -> {
            UserDetails win = new UserDetails(null);
            UI.getCurrent().addWindow(win);

        });


        Button editUser = new Button("User Bearbeiten");
        editUser.addClickListener(evt -> {
           UserDetails win = new UserDetails(grid.getSelectedItems().iterator().next());
           UI.getCurrent().addWindow(win);
        });


        HorizontalLayout hl = new HorizontalLayout();

        hl.addComponent(addUser);
        hl.addComponent(editUser);
        layout.addComponent(hl);



    }

    public void addUser(Person p) {
        persons.add(p);
        grid.setItems(persons);
    }

    public void removeUser(Person p){
        persons.remove(p);
        grid.setItems(persons);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


    }
}
