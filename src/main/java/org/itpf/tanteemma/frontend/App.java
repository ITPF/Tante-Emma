package org.itpf.tanteemma.frontend;

import java.util.List;
import java.util.Scanner;

import org.itpf.tanteemma.backend.configuration.PropertiesConfig;
import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.models.*;
import org.itpf.tanteemma.backend.services.DeliveryService;
import org.itpf.tanteemma.backend.services.OrderService;
import org.itpf.tanteemma.backend.services.PersonService;

/**
 * Used for some test runs etc.
 */
class App {
    private PropertiesConfig props;
    private PersonService personService;
    private DeliveryService deliveryService;
    private OrderService orderService;

    private Person currentUser;
    private Bestellung currentOrder;

    App() {
        props = new PropertiesConfig();
        DBHandler dbHandler = new DBHandler(props.get("jdbc.url"), props.get("jdbc.database"), props.get("jdbc.driver"), props.get("jdbc.user"), props.get("jdbc.password"));
        personService = new PersonService(dbHandler);
        deliveryService = new DeliveryService(dbHandler);
        orderService = new OrderService(dbHandler);

        currentUser = null;
        currentOrder = null;
    }

    void runConsole() {
        showLoginMenu();
    }

    private void showLoginMenu() {
        while(true) {
            currentUser = null;
            clearConsole();
            println("*** *** *** Tante Emma's Laden *** *** ***");
            println("------------------------------------------");
            println("** *** *** *** *** Lgin *** *** *** *** **\n");
            print("Vorname eingeben: ");
            String vorname = strPut();
            print("Nachnamen eingeben: ");
            String name = strPut();
            currentUser = personService.findPersonByName(vorname, name);
            if (currentUser != null) {
                showMainMenu();
            } else {
                print("Benutzer nicht gefunden! Wollen Sie sich registrieren? [ja/nein]");
                String choice = strPut();
                if (choice.equals("ja")) {
                    showRegistrationMenu();
                    showMainMenu();
                } else if (choice.equals("nein")) {
                    // repeat
                }
                else {
                    println("Sie haben eine falsche Eingabe getätigt.");
                    pressAnyKeyToContinue();
                }
            }
        }
    }

    private void showRegistrationMenu() {
        clearConsole();
        println("*** *** *** Tante Emma's Laden *** *** ***");
        println("------------------------------------------");
        println("** *** *** ** Registrierung *** *** *** **");
        currentUser = getUserInfo();
        personService.createNewPerson(currentUser);
    }

    private void showUpdateUserView() {
        clearConsole();
        println("*** *** *** Tante Emma's Laden *** *** ***");
        println("------------------------------------------");
        println("** *** *** *** Daten ändern *** *** *** **");
        currentUser = getUserInfo();
        personService.updatePerson(currentUser);
    }

    private Person getUserInfo() {
        Person person = new Person();
        boolean correctPerson;
        do  {
            print("\n*Vorname: ");
            person.setV_vorname(strPut());
            print("*Nachname: ");
            person.setV_name(strPut());
            person.setV_geschlecht(putin("Geschlecht: "));
            person.setV_telefon(putin("Telefon: "));
            person.setV_email(putin("Email: "));
            person.setV_rolle("Kunde");
            person.setV_strasse(putin("Strasse: "));
            person.setV_ort(putin("Ort: "));
            person.setV_plz(putin("PLZ: "));
            correctPerson = !person.getV_vorname().isEmpty() && !person.getV_name().isEmpty();
            if(!correctPerson) {
                println("Alle Pflichtfelder (*) müssen ausgefüllt sein!");
            }
        } while(correctPerson);
        return person;
    }

    private void showOrderMenu() {
        int menu = -1;
        clearConsole();
        while(menu != 0) {
            println("*** *** *** Tante Emma's Laden *** *** ***");
            println("------------------------------------------");
            println("** *** *** *** Bestellung *** *** *** **");
            currentOrder = orderService.findOpenBestellungByPerson(currentUser);
            if (currentOrder == null || currentOrder.getId() == 0 || currentOrder.getState().equals("ready")) {
                currentOrder = orderService.createNewBestelung(currentUser);
            }
            println("Wahrenkorb: " + orderService.getCountArtikelInBestellung(currentOrder) + "\n");
            println("[0] Zurück");
            println("[1] Alle Artikel ansehen");
            println("[2] Artikel bestellen");
            println("[3] Bestellng abbrechen");
            println("[4] Bestellung aufgeben");
            println("[5] Wahrenkorb einsehen");

            print("\n Ihre Wahl: ");
            menu = intPut();
            if(menu == 0) {
                break;
            } else if(menu == 1) {
                println("------------------------------------------");
                for (Artikel artikel : orderService.getAllArtikel()) {
                    println("ID: " + artikel.getPn_id() + " " + artikel.getV_artikelname() + " - " + artikel.getV_bezeichnung() + " - Bestand: " + artikel.getN_menge() + " - " + artikel.getN_preis() + "EUR");
                }
                println("------------------------------------------");
            } else if(menu == 2) {
                println("Geben Sie die ID und die Menge an: ");
                print("ID: ");
                long id = intPut();
                Artikel artikel = orderService.findArtikelById(id);
                if(artikel != null && artikel.getPn_id() != 0) {
                    print("Menge: ");
                    int menge = intPut();
                    orderService.addArtikelToBestellung(currentOrder, artikel, menge);
                    pressAnyKeyToContinue();
                }
            } else if(menu == 3) {
                clearConsole();
                String ja = putin("Sind Sie sicher dass Sie die Bestellung abbrechen wollen?");
                if(ja.equals("ja")) {
                    orderService.cancelBestellung(currentOrder);
                    currentOrder = null;
                }
            } else if(menu == 4) {
                if(currentOrder != null && currentOrder.getId() != 0 && orderService.getCountArtikelInBestellung(currentOrder) != 0) {
                    Auslieferung auslieferung = orderService.orderBestellung(currentOrder);
                    if(auslieferung != null && auslieferung.getId() != 0)
                        println("Ihre Bestellung wurde erfolgreich aufgenommen. Ein Mitarbeiter kümmert sich drum!");
                } else {
                    println("Es gibt nichts zu bestellen :)");
                }
                pressAnyKeyToContinue();
            } else if(menu == 5) {
                clearConsole();
                List<Bestellzuordnung> zuordnungen = orderService.getAllZuordnungenOfBestellung(currentOrder);
                if(zuordnungen.size() != 0) {
                    println("BestellungsID: " + currentOrder.getId());
                    this.printBestellZuordnungen(zuordnungen);
                }
                else {
                    println("Ihr Warenkorb ist leer!");
                }
                pressAnyKeyToContinue();
            }
        }
    }

    private void printBestellZuordnungen(List<Bestellzuordnung> zuordnungen) {
        int money = 0;
        println("------------------------------------------");
        for (Bestellzuordnung zuordnung : zuordnungen) {
            println("Artikel: " + zuordnung.getArtikel().getV_artikelname() + " " + zuordnung.getMenge() + "x");
            money += zuordnung.getArtikel().getN_preis() + zuordnung.getMenge();
        }
        println("------------------------------------------");
        println("Gesamt: " + money + " EUR");
    }

    private void showDelivery() {
        clearConsole();
        println("*** *** *** Tante Emma's Laden *** *** ***");
        println("------------------------------------------");
        println("** *** ** *** Auslieferungen  *** ** ** **");
        List<Auslieferung> auslieferungList = deliveryService.findAllDeliveriesByPerson(currentUser);
        if(auslieferungList.size() == 0) {
            println("Alles ausgeliefert :)");
        } else {
            int i = 01;
            for (Auslieferung auslieferung : auslieferungList) {
                println("AuslieferungsNr. " + i);
                println(auslieferung.getBestellung().getPerson().getV_vorname() + " " +
                auslieferung.getBestellung().getPerson().getV_name());
                println(auslieferung.getPerson().getV_strasse());
                println(auslieferung.getPerson().getV_plz() + " " + auslieferung.getBestellung().getPerson().getV_ort());
                println("");
                List<Bestellzuordnung> bestellzuordnungList = orderService.getAllZuordnungenOfBestellung(auslieferung.getBestellung());
                if(bestellzuordnungList.size() > 0) {
                    this.printBestellZuordnungen(bestellzuordnungList);
                } else {
                    println("Ein leerer Auftrag. Dieser Kunde ist ein Spaßvogel.");
                }
                i++;
            }
        }
        pressAnyKeyToContinue();
    }

    private void showMainMenu() {
        int menu = -1;
        boolean isAdmin = currentUser != null && currentUser.getV_rolle().equals("Mitarbeiter");
        while(menu != 0) {
            clearConsole();
            println("*** *** *** Tante Emma's Laden *** *** ***");
            println("------------------------------------------");
            println("Hallo " + currentUser.getV_vorname());
            println("** *** *** *** *** Menu *** *** *** *** **");
            println("[0] Ausloggen");
            println("[1] Meine Benutzerdaten ändern");
            println("[2] Bestellen");
            if(isAdmin) {
                println("[3] Meine Auslieferungen einsehen");
            }

            print("\nIhre Wahl: ");
            menu = intPut();

            switch (menu) {
                case 0:
                    // break
                    break;
                case 1:
                    showUpdateUserView();
                    break;
                case 2:
                    showOrderMenu();
                    break;
                case 3:
                    if(isAdmin) {
                        showDelivery();
                    }
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }

    private void println(String str) {
        System.out.println(str);
    }
    private void print(String str) {
        System.out.print(str);
    }

    private void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                //Runtime.getRuntime().exec("cls");
                System.out.println("\n\n\n\n\n\n\n");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private String putin(String str) { // guter mann
        print(str);
        return strPut();
    }

    private int intPut() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    private String strPut() {
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    private void pressAnyKeyToContinue()
    {
        System.out.println("Drücken Sie eine beliebige Taste...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

}
