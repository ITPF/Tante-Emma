package org.itpf.tanteemma.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.itpf.tanteemma.backend.models.Auslieferung;
import org.itpf.tanteemma.backend.models.Bestellung;
import org.itpf.tanteemma.backend.models.Bestellzuordnung;
import org.itpf.tanteemma.backend.services.DeliveryService;
import org.itpf.tanteemma.backend.services.OrderService;
import org.itpf.tanteemma.frontend.EntryPoint;
import org.itpf.tanteemma.frontend.customobjects.NavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class Orders extends VerticalLayout implements View {

    public static final String VIEW_NAME = "orders";

    public List<Bestellung> bestellungen;

    public Grid<Bestellung> grid;

    public Orders() {
        addComponent(new NavigationBar());

        bestellungen = new ArrayList<>();
        grid = new Grid<>();

        grid.addColumn(Bestellung::getId).setCaption("ID");
        grid.addColumn(Bestellung::getState).setCaption("State");

        addComponent(grid);


    }

    public void addBestellung(Bestellung bestellung, List<Bestellzuordnung> bzs) {
        OrderService service = OrderService.getInstance();
//        service.orderBestellung(bestellung);
        for(Bestellzuordnung bz : bzs){
//            service.addArtikelToBestellung(bestellung, bz.getArtikel(), bz.getMenge());
        }
        bestellungen.add(bestellung);
        grid.setItems(bestellungen);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


    }

}
