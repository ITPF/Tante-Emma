package org.itpf.tanteemma.frontend.windows;

import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.itpf.tanteemma.backend.models.Artikel;
import org.itpf.tanteemma.backend.models.Bestellung;
import org.itpf.tanteemma.backend.models.Bestellzuordnung;
import org.itpf.tanteemma.frontend.EntryPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class ShoppingCart extends Window {


    private Grid<Bestellzuordnung> grid;

    private List<Bestellzuordnung> bestellzuordnungen;

    private Bestellung bestellung;

    public ShoppingCart() {

        super("Einkaufswagen");

        bestellung = new Bestellung();
        bestellzuordnungen = new ArrayList<>();
        grid = new Grid<>();
        grid.addColumn(bestellzuordnung -> bestellzuordnung.getArtikel().getV_artikelname()).setCaption("Artikel");
        grid.addColumn(bestellzuordnung -> bestellzuordnung.getArtikel().getV_bezeichnung()).setCaption("Bezeichnung");

//        grid.addColumn(Bestellzuordnung::getMenge).setCaption("Menge");


        VerticalLayout layout = new VerticalLayout();

        setContent(layout);

        layout.addComponent(grid);


        HorizontalLayout hl = new HorizontalLayout();
        layout.addComponent(hl);

        Button remove = new Button("Entfernen");
        remove.addClickListener(evt -> {
            bestellzuordnungen.removeAll(grid.getSelectedItems());
            grid.setItems(bestellzuordnungen);
        });

        hl.addComponent(remove);

        Button order = new Button("Bestellen");
        order.addClickListener(event -> {
            EntryPoint.orders.addBestellung(bestellung, bestellzuordnungen);
            clearBestellung();

        });

        hl.addComponent(order);
        hl.setComponentAlignment(order, Alignment.MIDDLE_RIGHT);


        enableEditableAnmout();


    }

    public void clearBestellung() {
        bestellung = new Bestellung();
        bestellzuordnungen = new ArrayList<>();
        grid.setItems(bestellzuordnungen);
    }

    public void addProduct(Artikel a) {
        bestellzuordnungen.add(new Bestellzuordnung(0, bestellung, a, 1));
        grid.setItems(bestellzuordnungen);

    }

    private void enableEditableAnmout() {
        TextField field = new TextField();

        Binder<Bestellzuordnung> binder = grid.getEditor().getBinder();
        Binder.Binding<Bestellzuordnung, String> binding = binder.bind(field, (bestellzuordnung) -> String.valueOf(
                bestellzuordnung.getMenge()), (bestellzuordnung, fieldValue) -> bestellzuordnung.setMenge(
                Integer.parseInt(fieldValue)));


//        Binder.Binding<Bestellzuordnung, String> binding2 = binder.bind(field, new ValueProvider<Bestellzuordnung, String>() {
//            @Override
//            public String apply(Bestellzuordnung bestellzuordnung) {
//                return String.valueOf(bestellzuordnung.getMenge());
//            }
//        }, new Setter<Bestellzuordnung, String>() {
//            @Override
//            public void accept(Bestellzuordnung bestellzuordnung, String s) {
//                bestellzuordnung.setMenge(Integer.parseInt(s));
//            }
//        });


        Grid.Column<Bestellzuordnung, Integer> col = grid.addColumn(Bestellzuordnung::getMenge).setCaption("Menge");
        col.setEditorBinding(binding);
        col.setEditable(true);


        grid.getEditor().setEnabled(true);
    }
}
