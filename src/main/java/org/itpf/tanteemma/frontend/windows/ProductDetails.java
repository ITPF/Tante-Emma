package org.itpf.tanteemma.frontend.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.itpf.tanteemma.backend.models.Artikel;
import org.itpf.tanteemma.backend.services.OrderService;

/**
 * Created by Lindner.Patrick on 13.06.2017.
 */
public class ProductDetails extends Window {

    private boolean edit = false;

    public ProductDetails(Artikel a) {

        super("Artikel Details");

        if(a != null){
            edit = true;
        }

        VerticalLayout layout = new VerticalLayout();

        TextField name = new TextField("Artikelname");
        layout.addComponent(name);

        TextField bez = new TextField("Artikelbezeichnung");
        layout.addComponent(bez);

        TextField price = new TextField("Preis");
        layout.addComponent(price);

        TextField anmout = new TextField("Mänge");
        layout.addComponent(anmout);

        Button ok = new Button("Speichern");
        ok.addClickListener(evt -> {


        });





    }
}
