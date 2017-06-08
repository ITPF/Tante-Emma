package org.itpf.tanteemma.frontend.customobjects;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.itpf.tanteemma.frontend.EntryPoint;
import org.itpf.tanteemma.frontend.views.Orders;
import org.itpf.tanteemma.frontend.views.Products;
import org.itpf.tanteemma.frontend.views.Users;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class NavigationBar extends MenuBar {


    public NavigationBar() {

        MenuBar.Command navToUsers = menuItem -> EntryPoint.navigator.navigateTo(Users.VIEW_NAME);

        MenuBar.Command navToOrders = menuItem -> EntryPoint.navigator.navigateTo(Orders.VIEW_NAME);

        MenuBar.Command navToProducts = menuItem -> EntryPoint.navigator.navigateTo(Products.VIEW_NAME);

        MenuBar.Command openSC = menuItem -> {

            if(!EntryPoint.shoppingCart.isAttached()){
                UI.getCurrent().addWindow(EntryPoint.shoppingCart);
            }
        };


        MenuBar.MenuItem products = addItem("Produkte", navToProducts);

        MenuBar.MenuItem users = addItem("Personenverwaltung", navToUsers);

        MenuBar.MenuItem orders = addItem("Meine Bestellungen", navToOrders);

        MenuBar.MenuItem sc = addItem("Einkauswagen", openSC);


    }
}
