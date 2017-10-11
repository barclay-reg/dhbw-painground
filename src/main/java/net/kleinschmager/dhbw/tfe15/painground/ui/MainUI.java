/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe15.painground.persistence.repository.MemberProfileRepository;

/**
 * @author robertkleinschmager
 *
 */
@SpringUI
@Theme("valo")
@Viewport("initial-scale=1, maximum-scale=1")
@Title("DHBW Painground - PeopleSkill")
@Push
public class MainUI extends UI {

	MemberProfileRepository repo;
	Grid<MemberProfile> grid;
	
	@Autowired
	public MainUI(MemberProfileRepository repo) {
	    this.repo = repo;
	    this.grid = new Grid<>(MemberProfile.class);
	}
	
	@Override
	protected void init(VaadinRequest request) {
	    setContent(grid);
	    
	    grid.setCaption("List of members");
	    grid.setWidth(100, Unit.PERCENTAGE);
	    
	    listCustomers();
	}

	private void listCustomers() {
	    grid.setItems(repo.findAll());
	}
}
