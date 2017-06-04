package org.itpf.tanteemma.app;

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
import org.itpf.tanteemma.backend.services.OrderService;
import org.itpf.tanteemma.backend.services.PersonService;

/**
 * Used for some test runs etc.
 */
@SuppressWarnings("synthetic-access")
public class AppTest {
    Configurations configs;
    Configuration config;

    public AppTest() {
        configs = new Configurations();
        try {
            config = configs.properties(new File("src/main/resources/application.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        DBFiller filler = new DBFiller();
        filler.emptyDBAndFillWithJunk();

        OrderService orderService = new OrderService();
        PersonService personService = new PersonService();
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
