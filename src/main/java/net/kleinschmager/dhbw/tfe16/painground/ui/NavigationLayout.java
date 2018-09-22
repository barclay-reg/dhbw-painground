package net.kleinschmager.dhbw.tfe16.painground.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;

public class NavigationLayout {
   
	public SplitLayout getNavigationLayout(Component content) {
	   
	   Image image = new Image();
	   image.getStyle().set("max-width", "250px");
	   image.getStyle().set("max-heigth", "250px");
	   image.getStyle().set("display", "block");
	   image.getStyle().set("margin-left", "auto");
	   image.getStyle().set("margin-right", "auto");
	   image.getStyle().set("border-radius", "50%");
	   image.getStyle().set("border-style", "groove");
	  
	   image.setSrc("https://cdn.pixabay.com/photo/2015/03/04/22/35/head-659652_960_720.png");
	   
	   String name = "angemeldeter Benutzer: " + UserContext.username; ;
      Label username = new Label(name);
      username.getStyle().set("text-align", "center");
      
      	   
		Button button1 = new Button("Dashboard");
		button1.setTabIndex(1);
		
		button1.getStyle().set("max-height", "30px");
		button1.getStyle().set("box-shadow", "3px 4px 5px rgba(0, 0, 0, .6)");
		button1.getStyle().set("background-color", "white");
		button1.getStyle().set("color", "black");
		button1.addClickListener(event -> {
			button1.getUI().ifPresent(ui -> {
				ui.navigate("dashboard");
			});
		});
		button1.setSizeFull();

		Button button2 = new Button("Profiles");
		button2.setTabIndex(2);
      button2.getStyle().set("max-height", "30px");
      button2.getStyle().set("box-shadow", "3px 4px 5px rgba(0, 0, 0, .6)");
      button2.getStyle().set("background-color", "white");
      button2.getStyle().set("color", "black");
		button2.addClickListener(event -> {
			button1.getUI().ifPresent(ui -> {
				ui.navigate("allprofiles");
			});
		});
		button2.setSizeFull();

		Button button3 = new Button("Add Profile");
		button3.setTabIndex(3);
      button3.getStyle().set("max-height", "30px");
      button3.getStyle().set("box-shadow", "3px 4px 5px rgba(0, 0, 0, .6)");
      button3.getStyle().set("background-color", "white");
      button3.getStyle().set("color", "black");
		button3.addClickListener(event -> {
			button1.getUI().ifPresent(ui -> {
				ui.navigate("addProfile");
			});
		});
		button3.setSizeFull();

		VerticalLayout buttons = new VerticalLayout();
		buttons.getStyle().set("min-width", "250px");
		buttons.getStyle().set("max-width", "500px");
		buttons.add(image);
		buttons.add(username);
		buttons.add(button1, button2, button3);
		buttons.setAlignItems(Alignment.CENTER);
		
		SplitLayout layout = new SplitLayout(buttons, content);
		layout.setSizeFull();
		layout.setHeight("1080px");
		layout.setSplitterPosition(20);
		layout.setPrimaryStyle("background", "#33A4B2");
		return layout;

	}

}
