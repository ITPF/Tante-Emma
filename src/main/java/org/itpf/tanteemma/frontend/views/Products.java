package org.itpf.tanteemma.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.itpf.tanteemma.backend.models.Artikel;
import org.itpf.tanteemma.frontend.EntryPoint;
import org.itpf.tanteemma.frontend.customobjects.NavigationBar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class Products extends VerticalLayout implements View {

    public static final String VIEW_NAME = "products";

    public Products() {
        addComponent(new NavigationBar());

        List<Artikel> products = Arrays.asList(new Artikel[]{new Artikel(1, "Wurst", "Wurstaufschnitt", 20.01, 500),
                                                             new Artikel(2, "Käse", "Ja", 20.01, 50),
                                                             new Artikel(3, "Fleisch", "So", 20.01, 400),
                                                             new Artikel(4, "Fisch", "Schnitt", 20.01, 300),
                                                             new Artikel(5, "Gemüse", "Test", 20.01, 600),});

        Grid<Artikel> grid = new Grid<>();

        grid.setItems(products);
        grid.addColumn(Artikel::getV_artikelname).setCaption("Artikelname");
        grid.addColumn(Artikel::getV_bezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Artikel::getN_preis).setCaption("Preis");




        addComponent(grid);


        Button order = new Button("In den Warenkorb");
        order.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(!EntryPoint.shoppingCart.isAttached()){
                    UI.getCurrent().addWindow(EntryPoint.shoppingCart);
                }
                for(Artikel a : grid.getSelectedItems()){
                    EntryPoint.shoppingCart.addProduct(a);
                }
            }
        });
        addComponent(order);

    }

    private void refreshTable() {

    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


    }
}
