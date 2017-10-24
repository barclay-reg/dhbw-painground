/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

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
@SpringView( name= MemberProfileList.VIEW_NAME)
public class MemberProfileList extends HorizontalLayout implements View {

	private static final long serialVersionUID = -1824693214883003192L;

	public static final String VIEW_NAME = "memberprofile-list";
	
	@Autowired
	MemberProfileRepository repo;
	Grid<MemberProfile> grid;
	
	
	public MemberProfileList() {
	    this.grid = new Grid<>(MemberProfile.class);
	}
	
	
	@PostConstruct
    private void init() {
		
	    grid.setCaption("List of Profiles");
	    grid.setWidth(100, Unit.PERCENTAGE);
	    
		addComponent(grid);
	    
	    listCustomers();
		
	}
	
	private void listCustomers() {
	    grid.setItems(repo.findAll());
	}

}
