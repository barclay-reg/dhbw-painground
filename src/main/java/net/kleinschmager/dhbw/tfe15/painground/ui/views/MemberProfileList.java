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

	// the app-layout default view must have the name ""
	public static final String VIEW_NAME = "member-profile-list-view";
	
	public static final String MAIN_COMPONENT_ID = "member-profile-list";
	
	@Autowired
	MemberProfileRepository repo;
	Grid<MemberProfile> grid;
	
	
	public MemberProfileList() {
	    this.grid = new Grid<>(MemberProfile.class);
	}
	
	
	@PostConstruct
    private void init() {
		
		setSizeFull();

		grid.setId(MAIN_COMPONENT_ID);
	    grid.setSizeFull();
	    grid.setColumnOrder("id", "memberId", "surName", "givenName", "dateOfBirth", "skills");
		
		addComponent(grid);
	    
	    loadAndDisplayMemberProfiles();
	}
	
	private void loadAndDisplayMemberProfiles() {
	    grid.setItems(repo.findAll());
	}

}
