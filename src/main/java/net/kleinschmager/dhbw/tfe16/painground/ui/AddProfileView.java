package net.kleinschmager.dhbw.tfe16.painground.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import net.kleinschmager.dhbw.tfe16.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe16.painground.persistence.repository.MemberProfileRepository;

@Route(value = "addProfile", layout = MainView.class)
public class AddProfileView extends Div {
	
	private MemberProfileRepository profileRepo;

	public AddProfileView(@Autowired MemberProfileRepository profileRepository) {
		this.profileRepo = profileRepository;
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		
		
		TextField givenName = new TextField("Name", "Your given name");
		TextField sureName = new TextField("Surname", "Your surname");
		//sureName.setRequired(true);
		TextField memberId = new TextField("Member Id", "Your internal unique id");
		//memberId.setRequired(true);
		DatePicker dateOfBirth = new DatePicker("Date of Birth");
		
		Binder<MemberProfile> binder =  new Binder<>();
		
		MemberProfile newProfile = new MemberProfile();
		
		binder.forField(givenName).bind(MemberProfile::getGivenName, MemberProfile::setGivenName);
		binder.forField(sureName)
		.asRequired("Surname must be given")
		.bind(MemberProfile::getSurName, MemberProfile::setSurName);
		binder.forField(memberId)
		.asRequired("MemberId must be given")
		.bind(MemberProfile::getMemberId, MemberProfile::setMemberId);
		binder.forField(dateOfBirth)
			.withConverter(new LocalDateToDateConverter())
			.bind(MemberProfile::getDateOfBirth, MemberProfile::setDateOfBirth);
		
		Button save = new Button("Save",
		        new Icon(VaadinIcon.CHECK_CIRCLE_O));
		Button reset = new Button("Reset",
		        new Icon(VaadinIcon.ARROW_CIRCLE_LEFT_O));
		
		// Click listeners for the buttons
		save.addClickListener(event -> {
		    if (binder.writeBeanIfValid(newProfile)) {
		        this.profileRepo.saveAndFlush(newProfile);
		    } else {
		        
		        //infoLabel.setText("There are errors: " + errorText);
		    }
		});
		reset.addClickListener(event -> {
		    // clear fields by setting null
			
		    binder.readBean(null);
		});
		
		
		// Button bar
		HorizontalLayout actions = new HorizontalLayout();
		actions.add(reset, save);
		actions.setVerticalComponentAlignment(Alignment.END, reset, save);
		
		
		FormLayout formLayout = new FormLayout(givenName, sureName, memberId, dateOfBirth, actions);
		formLayout.getStyle().set("margin-left", "10px");
		formLayout.getStyle().set("margin-right", "10px");		
				
		
		//add(formLayout);
		//add(actions);
		//formLayout.add(components);

		add(formLayout);
				
	}

}
