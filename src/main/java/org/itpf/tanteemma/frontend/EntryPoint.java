package org.itpf.tanteemma.frontend;

import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

/**
 * Created by Patrick on 01.06.2017.
 */

@Title("Tante Emma Laden")
public class EntryPoint extends UI {


    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Display the greeting
        content.addComponent(new Label("Hello World!"));

        // Have a clickable button
        content.addComponent(new Button("Push Me!",
                click -> Notification.show("Pushed!")));


    }


}
