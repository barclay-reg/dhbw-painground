package net.kleinschmager.dhbw.tfe.painground.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyDownEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@Route("")
@Theme(Lumo.class)
public class LoginView extends VerticalLayout {

	
	private Button loginButton = new Button("Login");
	private TextField txtUsername = new TextField("Benutzername");
	private PasswordField txtPassword = new PasswordField("Passwort");
	private Label headline = new Label("EmplyeeSkill");
	private UserContext userContext;

	public LoginView(@Autowired UserContext userContext) 
	{
		this.userContext = userContext;
		createLoginForm();
	}

	private void createLoginForm() 
	{
		getStyle().set("align-items", "center");
		getStyle().set("background-color", "#efeaec");
		getStyle().set("height", "100vh");
		//getStyle().set("width", "100vw");
		VerticalLayout basicLayout = new VerticalLayout();
		add(basicLayout);
		basicLayout.getStyle().set("position", "relative");
		basicLayout.getStyle().set("top", "25%");
		basicLayout.getStyle().set("align", "center");
		basicLayout.getStyle().set("border", "1px solid black");
		basicLayout.getStyle().set("border-radius", "10px");
		basicLayout.getStyle().set("background-color", "#ffffff");
		basicLayout.setAlignItems(Alignment.CENTER);
		
		Image appImage = new Image();
		appImage.setSrc(getImagePath());
		
//		basicLayout.add(appImage);
		basicLayout.add(headline);
		basicLayout.add(new Label());
		basicLayout.add(txtUsername);
		basicLayout.add(txtPassword);
		basicLayout.add(loginButton);

		
	    addListener(KeyDownEvent.class, event ->
	    {
	    	if(Key.ENTER.equals(event.getKey()))
	    	{
	    		setUserNameToContext(txtUsername.getValue());
	    		getUI().get().navigate("dashboard");
	    	}
	    });
		
		loginButton.addClickListener(new ComponentEventListener() 
		{

			@Override
			public void onComponentEvent(ComponentEvent event) 
			{
				 setUserNameToContext(txtUsername.getValue());
				 getUI().get().navigate("profiles");	
			}

		});
	}
	
	private void setUserNameToContext(String username) 
	{
		UserContext.username = username;
	}
	
	private String getImagePath()
	{
		String path = "/applicationIcon.png";
		return path;
	}
}
