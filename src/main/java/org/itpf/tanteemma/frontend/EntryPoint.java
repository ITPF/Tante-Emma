package org.itpf.tanteemma.frontend;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.itpf.tanteemma.frontend.customobjects.NavigationBar;
import org.itpf.tanteemma.frontend.views.Orders;
import org.itpf.tanteemma.frontend.views.Products;
import org.itpf.tanteemma.frontend.views.Users;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Patrick on 01.06.2017.
 */

@Title("Tante Emma Laden")
public class EntryPoint extends UI {

    public static Navigator navigator;


    protected void init(VaadinRequest vaadinRequest) {


        navigator = new Navigator(this, this);
        navigator.addView(Users.VIEW_NAME, new Users());
        navigator.addView(Orders.VIEW_NAME, new Orders());
        navigator.addView(Products.VIEW_NAME, new Products());




        VerticalLayout content = new VerticalLayout();
        setContent(content);

        content.addComponent(new NavigationBar());







    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EntryPoint.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {


    }


}
