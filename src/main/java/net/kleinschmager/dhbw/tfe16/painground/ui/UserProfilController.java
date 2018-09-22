package net.kleinschmager.dhbw.tfe16.painground.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class UserProfilController {

   @Autowired
   private UserContext userContext;

   public Component getUserProfil() {
      VerticalLayout basicComponent = new VerticalLayout();
      basicComponent.setClassName("UserProfil");
      Icon personIcon = new Icon(VaadinIcon.USER);
      personIcon.setSize("128pt");
      personIcon.getStyle().set("size", "80pt");
      personIcon.getStyle().set("border", "1px solid black");
      personIcon.getStyle().set("border-radius", "100%");

      basicComponent.add(personIcon);
      String name = "angemeldeter Benutzer: " + UserContext.username;
      Label username = new Label(name);
      basicComponent.add(username);

      return basicComponent;
   }

   private Icon getUserIcon(Long userid) {

      return null;
   }
}
