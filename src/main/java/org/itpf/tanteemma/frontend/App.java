package org.itpf.tanteemma.frontend;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.models.Artikel;
import org.itpf.tanteemma.backend.models.Auslieferung;
import org.itpf.tanteemma.backend.models.Bestellung;
import org.itpf.tanteemma.backend.models.Person;
import org.itpf.tanteemma.backend.persistence.DBFiller;
import org.itpf.tanteemma.backend.services.DeliveryService;
import org.itpf.tanteemma.backend.services.OrderService;
import org.itpf.tanteemma.backend.services.PersonService;

/**
 * Used for some test runs etc.
 */
@SuppressWarnings("synthetic-access")
public class App {
    Configurations configs;
    Configuration config;

    public App() {
        configs = new Configurations();
        try {
            config = configs.properties(new File("src/main/resources/application.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        DBHandler dbHandler = new DBHandler();
        DBFiller filler = new DBFiller(dbHandler);
        DeliveryService deliveryService = new DeliveryService(dbHandler);
        filler.emptyDBAndFillWithJunk();

        OrderService orderService = new OrderService(dbHandler);
        PersonService personService = new PersonService(dbHandler);
        Person igor = personService.findPersonsByName("Igor", "Stoljarow");
        Bestellung igorsBestellung = orderService.createNewBestelung(igor);
        orderService.addArtikelToBestellung(igorsBestellung, "Klopapier", 10);
        List orders = orderService.addArtikelToBestellung(igorsBestellung, "Bananen", 1);

        orderService.cancelBestellung(igorsBestellung);
        igorsBestellung = orderService.createNewBestelung(igor);
        orderService.addArtikelToBestellung(igorsBestellung, "Gurken", 2);
        orders = orderService.addArtikelToBestellung(igorsBestellung, "Airways Kaugummi", 2);

        Auslieferung igorsAuslieferung = orderService.orderBestellung(igorsBestellung);

        System.out.println("success");
    }
}
