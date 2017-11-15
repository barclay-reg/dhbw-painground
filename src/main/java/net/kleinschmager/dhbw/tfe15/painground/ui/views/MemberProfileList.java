/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe15.painground.persistence.repository.MemberProfileRepository;

/**
 * {@link View} to show the list of all {@link MemberProfile}
 * 
 * @author robertkleinschmager
 *
 */
@SpringView( name = MemberProfileList.VIEW_NAME)
@MenuCaption("Profiles")
@MenuIcon(VaadinIcons.LIST)
public class MemberProfileList extends HorizontalLayout implements View {

	private static final long serialVersionUID = -1824693214883003192L;

	// the appl-layout default view must have the name ""
	public static final String VIEW_NAME = "";
	
	@Autowired
	MemberProfileRepository repo;
	Grid<MemberProfile> grid;
	
	
	public MemberProfileList() {
	    this.grid = new Grid<>(MemberProfile.class);
	}
	
	
	@PostConstruct
    private void init() {
		
		setSizeFull();

		grid.setCaption("List of Profiles");
	    //grid.setWidth(100, Unit.PERCENTAGE);
	    grid.setSizeFull();
		
		addComponent(grid);
	    
	    listCustomers();
	    
	    // mehr ist nicht zu tun
		
	}
	
	private void listCustomers() {
	    grid.setItems(repo.findAll());
	}

}
