package org.itpf.tanteemma.backend.handler;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class DBHandlerTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void dropTable() throws Exception {
    }

    @Test
    public void dropAlltables() throws Exception {
    }

    @Test
    public void createAllTables() throws Exception {
    }

    @Test
    public void createPersonTable() throws Exception {
    }

    @Test
    public void insertSomeNewPersons() throws Exception {
    }

    @Test
    public void insertNewPerson() throws Exception {
    }

    @Test
    public void selectPerson() throws Exception {
    }

    @Test
    public void selectPersonByName() throws Exception {
    }

    @Test
    public void selectAllFromPerson() throws Exception {
    }

    @Test
    public void selectAllPersonsByRolle() throws Exception {
    }

    @Test
    public void createArtikelTable() throws Exception {
    }

    @Test
    public void insertNewArtikel() throws Exception {
    }

    @Test
    public void insertSomeNewArtikels() throws Exception {
    }

    @Test
    public void selectAllArtikelFrombestellung() throws Exception {
    }

    @Test
    public void updateArtikel() throws Exception {
    }

    @Test
    public void selectArtikelByName() throws Exception {
    }

    @Test
    public void selectAllFromArtikel() throws Exception {
    }

    @Test
    public void createBestellungTable() throws Exception {
    }

    @Test
    public void insertNewBestellung() throws Exception {
    }

    @Test
    public void insertSomeBestellungen() throws Exception {
    }

    @Test
    public void deleteBestellung() throws Exception {
    }

    @Test
    public void selectBestellung() throws Exception {
    }

    @Test
    public void updateBestellung() throws Exception {
    }

    @Test
    public void createAuslieferungTable() throws Exception {
    }

    @Test
    public void insertNewAuslieferung() throws Exception {
    }

    @Test
    public void selectAuslieferung() throws Exception {
    }

    @Test
    public void selectAllFromAuslieferungByBestellungStatus() throws Exception {
    }

    @Test
    public void selectAllfromAusliefrungByPerson() throws Exception {
    }

    @Test
    public void createBestellzuordnungTable() throws Exception {
    }

    @Test
    public void insertNewBestellzuordnung() throws Exception {
    }

    @Test
    public void deleteBestellZuordnung() throws Exception {
    }

    @Test
    public void insertSomeNewBestellzuordnungen() throws Exception {
    }

    @Test
    public void selectAllBestellzurodnungenByBestellung() throws Exception {
    }

    @Test
    public void createDatabase() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DBHandler.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
