package org.itpf.tanteemma.backend.services;

import org.itpf.tanteemma.backend.configuration.PropertiesConfig;
import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.models.Artikel;
import org.itpf.tanteemma.backend.models.Auslieferung;
import org.itpf.tanteemma.backend.models.Bestellung;
import org.itpf.tanteemma.backend.models.Person;
import org.itpf.tanteemma.backend.persistence.DBFiller;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ServiceTest {
    private DeliveryService deliveryService;
    private OrderService orderService;
    private PersonService personService;
    private DBFiller dbFiller;
    private DBHandler dbHandler;

    @Before
    public void setUp() throws Exception {
        PropertiesConfig props = new PropertiesConfig();
        // String url, String databaseName, String jdbcDriverPath, String user, String password
        this.dbHandler = new DBHandler(props.get("jdbc.url"), "test", props.get("jdbc.driver"), props.get("jdbc.user"), props.get("jdbc.password"));
        this.deliveryService = new DeliveryService(dbHandler);
        this.orderService = new OrderService(dbHandler);
        this.personService = new PersonService(dbHandler);
        this.dbFiller = new DBFiller(dbHandler);
    }

    @Test
    public void testDbFillerAndHandler() {
        dbFiller.emptyDBAndFillWithJunk();

        Person igor = dbHandler.selectPersonByName("Igor", "Stoljarow");
        assertNotNull("Found person named Igor should exist", igor);
        assertTrue("Igors Strasse must be Teuschsweg 666", igor.getV_strasse().equals("Teuschsweg 666"));

        Artikel artikel = dbHandler.selectArtikelByName("Klopapier");
        assertNotNull("Artikel with name Klopapier mus exist", artikel);
        assertEquals("Artikels menge should be 100", 100, artikel.getN_menge());
    }

    @Test
    public void testServices() {
        Person igor = personService.findPersonsByName("Igor", "Stoljarow");
        assertNotNull("Igor must exist", igor);
        assertTrue("Igor's geschlecht must be unbekannt", igor.getV_geschlecht().equals("unbekannt"));
        Person filizTest = personService.findPersonsByName("FilizTest", "Neumann");
        assertNull("FilizTest should not exist", filizTest);

        Bestellung igorsBestellung = orderService.createNewBestelung(igor);
        assertNotNull("Igor'S Bestellung has been created", igorsBestellung);
        assertTrue("Igor's Bestellung should be open", igorsBestellung.getState().equals("open"));
        assertTrue("Igor's Bestellung is actually his order", igorsBestellung.getPerson().getPn_id() == igor.getPn_id());

        orderService.addArtikelToBestellung(igorsBestellung, "Klopapier", 10);
        List<Artikel> orders = orderService.addArtikelToBestellung(igorsBestellung, "Bananen", 1);
        assertEquals("The orders list should have exactly 2 items", 2, orders.size());
        assertNotNull("First element of orders must exist", orders.get(0));
        assertTrue("First element of orders should have the id 1", orders.get(0).getPn_id() == 1);
        assertTrue("Second element of orders should be Bananen", orders.get(1).getV_artikelname().equals("Bananen"));

        Artikel bananen = dbHandler.selectArtikelByName("Bananen");
        assertNotNull("Bananen should exist", bananen);
        assertTrue("Bananen Artikel should have menge 49", bananen.getN_menge() == 49);

        orderService.cancelBestellung(igorsBestellung);
        bananen = dbHandler.selectArtikelByName("Bananen");
        assertNotNull("After canceling order, Bananen should still exist", bananen);
        assertEquals("Now Bananane should have menge 50 again", 50, bananen.getN_menge());

        igorsBestellung = orderService.createNewBestelung(igor);
        orderService.addArtikelToBestellung(igorsBestellung, "Gurken", 2);
        orderService.addArtikelToBestellung(igorsBestellung, "Airways Kaugummi", 2);
        Artikel gurken = dbHandler.selectArtikelByName("Gurken");
        assertNotNull("Gurken should exist", gurken);
        assertEquals("Gurken should have menge 38", 38, gurken.getN_menge());

        Auslieferung igorsAuslieferung = orderService.orderBestellung(igorsBestellung);
        assertNotNull("Igor's Auslieferung shouldn't be null", igorsAuslieferung);

        List<Auslieferung> openDeliveries = deliveryService.findAllReadyDeliveries();
        assertTrue("There should be at least one ready delivery", openDeliveries.size() > 0);
        assertNotNull("The delivery has a deliverer", openDeliveries.get(0).getPerson());
        assertTrue("The deliverer is a valid person", openDeliveries.get(0).getPerson().getPn_id() != 0);
        assertNotNull("Delivery has an order", openDeliveries.get(0).getBestellung());
        assertTrue("The order is valid", openDeliveries.get(0).getBestellung().getId() != 0);
    }

    @After
    public void tearDown() throws Exception {
        //this.dbHandler.dropAlltables();
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(OrderService.class)
                .addClass(DeliveryService.class)
                .addClass(PersonService.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
