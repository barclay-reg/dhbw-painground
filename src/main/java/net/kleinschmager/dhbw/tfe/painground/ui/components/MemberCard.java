package net.kleinschmager.dhbw.tfe.painground.ui.components;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.server.StreamResource;

import net.kleinschmager.dhbw.tfe.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.ProjectMembership;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.Skill;

import java.io.ByteArrayInputStream;
import java.util.List;

@Tag("member-card")
@HtmlImport("src/member-card.html")
public class MemberCard extends PolymerTemplate<MemberCardModel> {

    public MemberCard(MemberProfile profile) {

		getModel().setName(profile.getGivenName());
		getModel().setSurName(profile.getSurName());
		// String dateOfBirth;
		// try {
		// dateOfBirth = profile.getDateOfBirth().toString();
		// dateOfBirth = dateOfBirth.split("\\s")[0];
		// } catch (Exception e) {
		// dateOfBirth = "";
		// }
		getModel().setRelevantSkills(profile.getTopThreeSkills());
		getModel().setProjects(profile.getMembershipTopThreeTimeOrder());

		if (profile.getPicture() != null) {
			StreamResource imageResource =
	        	    new StreamResource("initial-filename.png", () -> new ByteArrayInputStream( profile.getPicture() ));
	        
	        Element picture = new Element("object");
	        picture.setAttribute("slot", "picture");
	        picture.setAttribute("type", "image/png");
	        picture.setAttribute("data", imageResource);
	        // must add this style, to apply css settings within member-card.html
	        picture.setAttribute("style", "height: 100px; width: auto;");
	        
	        getElement().appendChild(picture);
		}
	}

	public List<Skill> getRelevantSkills() {
		return getModel().getRelevantSkills();
	}

	public List<ProjectMembership> getProjects() {
		return getModel().getProjects();
	}

}
