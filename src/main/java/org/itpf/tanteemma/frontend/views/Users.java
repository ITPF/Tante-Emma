package org.itpf.tanteemma.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.itpf.tanteemma.backend.models.Person;
import org.itpf.tanteemma.backend.services.PersonService;
import org.itpf.tanteemma.frontend.customobjects.NavigationBar;
import org.itpf.tanteemma.frontend.windows.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class Users extends VerticalLayout implements View {

    public static final String VIEW_NAME = "users";

    private Grid<Person> grid;

    private List<Person> persons;

    public Users() {

        persons = Arrays.asList(

                new Person[]{
                        new Person(1, "patrick", "lindner", "m", "123451111", "p@me.com", "rolle1", "Straße 1", "ort 1",
                                   "plz1"),
                        new Person(2, "tatjana", "kraemer", "w", "123451111", "t@me.com", "rolle2", "Straße 1", "ort 2",
                                   "plz1"),
                        new Person(3, "igor", "stoljarow", "m", "123451111", "i@me.com", "rolle3", "Straße 1", "ort 3",
                                   "plz1"),
                        new Person(4, "filiz", "neumann", "w", "123451111", "f@me.com", "rolle4", "Straße 1", "ort 4",
                                   "plz1")


                });


        addComponent(new NavigationBar());


        grid = new Grid<>();

        addComponent(grid);
        updateGrid();
        grid.addColumn(Person::getV_name).setCaption("Name");
        grid.addColumn(Person::getV_vorname).setCaption("Vorname");



        Button update = new Button("Update Table");
        update.addClickListener((Button.ClickListener) clickEvent -> updateGrid());

        Button addUser = new Button("Add User");
        addUser.addClickListener((Button.ClickListener) clickEvent -> {
            UserDetails win = new UserDetails();
            UI.getCurrent().addWindow(win);

        });



        HorizontalLayout hl = new HorizontalLayout();

        hl.addComponent(addUser);
        hl.addComponent(update);

        addComponent(hl);

    }

    private void updateGrid() {
//        persons = PersonService.getInstance().getAllPersons();
        grid.setItems(persons);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        Notification.show("User Page");

    }
}
