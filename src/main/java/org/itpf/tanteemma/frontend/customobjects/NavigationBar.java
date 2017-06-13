package org.itpf.tanteemma.frontend.customobjects;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import org.itpf.tanteemma.frontend.EntryPoint;
import org.itpf.tanteemma.frontend.views.Orders;
import org.itpf.tanteemma.frontend.views.Products;
import org.itpf.tanteemma.frontend.views.SignInView;
import org.itpf.tanteemma.frontend.views.Users;

import java.util.Map;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class NavigationBar extends MenuBar {


    private MenuBar.MenuItem users;

    public NavigationBar() {

        MenuBar.Command navToUsers = menuItem -> EntryPoint.navigator.navigateTo(Users.VIEW_NAME);

        MenuBar.Command navToOrders = menuItem -> EntryPoint.navigator.navigateTo(Orders.VIEW_NAME);

        MenuBar.Command navToProducts = menuItem -> EntryPoint.navigator.navigateTo(Products.VIEW_NAME);

        MenuBar.Command navToSingIn = menuItem -> {
            EntryPoint.navigator.navigateTo(SignInView.VIEW_NAME);
            EntryPoint.person = null;
            EntryPoint.shoppingCart.clearBestellung();
            if(EntryPoint.shoppingCart.isAttached()){
                EntryPoint.shoppingCart.close();
            }
        };

        MenuBar.Command openSC = menuItem -> {

            if (!EntryPoint.shoppingCart.isAttached()) {
                UI.getCurrent().addWindow(EntryPoint.shoppingCart);
            }
        };


        MenuBar.MenuItem products = addItem("Produkte", navToProducts);

        users = addItem("Personenverwaltung", navToUsers);

        MenuBar.MenuItem orders = addItem("Meine Bestellungen", navToOrders);

        MenuBar.MenuItem sc = addItem("Einkauswagen", openSC);

        MenuBar.MenuItem logout = addItem("Logout", navToSingIn);

    }

    public void enableAdmin(boolean b){
        users.setVisible(b);
    }
}
