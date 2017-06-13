package org.itpf.tanteemma.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.itpf.tanteemma.backend.models.Artikel;
import org.itpf.tanteemma.backend.models.Person;
import org.itpf.tanteemma.backend.services.OrderService;
import org.itpf.tanteemma.frontend.EntryPoint;
import org.itpf.tanteemma.frontend.customobjects.NavigationBar;
import org.itpf.tanteemma.frontend.windows.ProductDetails;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class Products extends Panel implements View {

    public static final String VIEW_NAME = "products";

    private NavigationBar bar;

    private Button orderButton;

    private Button editButton;

    private Button addButton;

    private List<Artikel> products;

    private Grid<Artikel> grid;

    public Products() {

        super("Produkte");

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        bar = new NavigationBar();

        layout.addComponent(bar);

//        List<Artikel> products = Arrays.asList(new Artikel[]{new Artikel(1, "Wurst", "Wurstaufschnitt", 20.01, 500),
//                                                             new Artikel(2, "Käse", "Ja", 20.01, 50),
//                                                             new Artikel(3, "Fleisch", "So", 20.01, 400),
//                                                             new Artikel(4, "Fisch", "Schnitt", 20.01, 300),
//                                                             new Artikel(5, "Gemüse", "Test", 20.01, 600),});

        grid = new Grid<>();

        products = OrderService.getInstance().getAllArtikel();

        grid.setItems(products);
        grid.addColumn(Artikel::getV_artikelname).setCaption("Artikelname");
        grid.addColumn(Artikel::getV_bezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Artikel::getN_preis).setCaption("Preis");


        layout.addComponent(grid);

        grid.setSizeFull();

        orderButton = new Button("In den Warenkorb");
        orderButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!EntryPoint.shoppingCart.isAttached()) {
                    UI.getCurrent().addWindow(EntryPoint.shoppingCart);
                }
                for (Artikel a : grid.getSelectedItems()) {
                    EntryPoint.shoppingCart.addProduct(a);
                }
            }
        });

        editButton = new Button("Artikel bearbeiten");
        editButton.addClickListener(evt -> {
            ProductDetails win = new ProductDetails(grid.getSelectedItems().iterator().next());
            UI.getCurrent().addWindow(win);
        });

        addButton = new Button("Artikel Hinzufügen");
        addButton.addClickListener(evt -> {
            ProductDetails win = new ProductDetails(null);
            UI.getCurrent().addWindow(win);
        });


        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(orderButton);
//        hl.addComponent(addButton);
//        hl.addComponent(editButton);

        layout.addComponent(hl);
        grid.setSizeFull();
        setWidth(50, Unit.PERCENTAGE);

    }





    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        products = OrderService.getInstance().getAllArtikel();
        grid.setItems(products);


        if (EntryPoint.person.getV_rolle().equals(Person.WORKER)) {
            addButton.setVisible(true);
            editButton.setVisible(true);
            bar.enableAdmin(true);
        } else if (EntryPoint.person.getV_rolle().equals(Person.KUNDE)) {
            addButton.setVisible(false);
            addButton.setVisible(false);
            bar.enableAdmin(false);
        }

    }
}
