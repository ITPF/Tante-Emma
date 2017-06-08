package org.itpf.tanteemma.frontend.customobjects;

import com.vaadin.ui.MenuBar;
import org.itpf.tanteemma.frontend.EntryPoint;
import org.itpf.tanteemma.frontend.views.Orders;
import org.itpf.tanteemma.frontend.views.Products;
import org.itpf.tanteemma.frontend.views.Users;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class NavigationBar extends MenuBar {


    public NavigationBar() {

        MenuBar.Command navToUsers = (Command) menuItem -> EntryPoint.navigator.navigateTo(Users.VIEW_NAME);

        MenuBar.Command navToOrders = menuItem -> EntryPoint.navigator.navigateTo(Orders.VIEW_NAME);

        MenuBar.Command navToProducts = menuItem -> EntryPoint.navigator.navigateTo(Products.VIEW_NAME);

        MenuBar.MenuItem products = addItem("Produkte", navToProducts);

        MenuBar.MenuItem users = addItem("Personenverwaltung", navToUsers);

        MenuBar.MenuItem orders = addItem("Meine Bestellungen", navToOrders);


    }
}
