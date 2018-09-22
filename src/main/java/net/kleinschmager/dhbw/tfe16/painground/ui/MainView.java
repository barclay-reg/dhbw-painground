package net.kleinschmager.dhbw.tfe16.painground.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import net.kleinschmager.dhbw.tfe16.painground.persistence.repository.MemberProfileRepository;

/**
 * The main view contains a simple label element and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route(value="main")
@Theme(Lumo.class)
public class MainView extends VerticalLayout {
	
    public MainView(@Autowired ExampleTemplate template, @Autowired UserProfilController userProfilController) {
    	    	
        // This is just a simple label created via Elements API
    	FlexLayout flexleft = new FlexLayout();
    	flexleft.add(userProfilController.getUserProfil());
    	FlexLayout flexright = new FlexLayout();
    	
    	SplitLayout splitlayout = new SplitLayout(flexleft,flexright);
        // This is a simple template example
        add(splitlayout, template);
		
    	NavigationLayout navLayout = new NavigationLayout();
        add(navLayout.getNavigationLayout(splitlayout), template);
        setClassName("main-layout");
    }

}
