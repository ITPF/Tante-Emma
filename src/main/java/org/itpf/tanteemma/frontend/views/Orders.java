package org.itpf.tanteemma.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.itpf.tanteemma.frontend.customobjects.NavigationBar;

/**
 * Created by Lindner.Patrick on 08.06.2017.
 */
public class Orders extends VerticalLayout implements View {

    public static final String VIEW_NAME = "orders";


    public Orders() {
        addComponent(new NavigationBar());


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


    }

}
