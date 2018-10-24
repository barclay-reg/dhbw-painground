
package net.kleinschmager.dhbw.tfe16.painground.ui;

import com.google.common.base.Preconditions;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import net.kleinschmager.dhbw.tfe16.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe16.painground.persistence.repository.MemberProfileRepository;
import net.kleinschmager.dhbw.tfe16.painground.ui.components.FuseCard;
import net.kleinschmager.dhbw.tfe16.painground.ui.components.MemberCard;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * View to show all profiles existing in the database
 */
@Route(value = "profiles", layout = MainView.class)
//@RouteAlias(value = "")
public class AllProfilesView extends Div {

    private MemberProfileRepository profileRepo;

    private List<MemberProfile> allMembers;

    public AllProfilesView(@Autowired MemberProfileRepository profileRepository) {

        Preconditions.checkNotNull(profileRepository);

        this.profileRepo = profileRepository;
        this.allMembers = profileRepo.findAll();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        List<Component> components = paintBoard();
        Component profileCards = createProfilelList(components);
        HorizontalLayout searchBar = createSearch();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(searchBar);
        verticalLayout.add(profileCards);
        
        add(verticalLayout);
    }

    private HorizontalLayout createSearch() {
        HorizontalLayout searchLayout = new HorizontalLayout();

        TextField searchBar = new TextField();
        searchBar.setPlaceholder("type a name or skill .... ");
        searchBar.getStyle().set("width", "300px");
        Icon searchIcon = new Icon(VaadinIcon.SEARCH);

        searchLayout.add(searchBar);
        searchLayout.add(searchIcon);

        searchLayout.getStyle().set("display", "block");
        searchLayout.getStyle().set("margin-left", "auto");
        searchLayout.getStyle().set("margin-right", "auto");
        searchLayout.getStyle().set("width", "360px");
        searchLayout.getStyle().set("box-shadow", "3px 4px 5px rgba(0, 0, 0, .6)");
        searchLayout.getStyle().set("border-radius", "10px");
        searchLayout.getStyle().set("background-color", "#1AB27C");


        return searchLayout;
    }

    private List<Component> paintBoard() {
        List<Component> components = new ArrayList<>();
        for (MemberProfile memberProfile : allMembers) {
            MemberCard card = new MemberCard(memberProfile);
            FuseCard cardWrapper = new FuseCard(card);
            components.add(cardWrapper);

        }
        return components;
    }

    private Component createProfilelList(List<Component> components) {
        FlexLayout layout = new FlexLayout();
        components.forEach(c -> layout.add(c));
        
        layout.setAlignItems(Alignment.START);
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        //horizontalLayout.set
        
        
        layout.getStyle().set("flex-direction", "row");
        layout.getStyle().set("flex-wrap", "wrap");
        
        layout.getStyle().set("align-content","space-between");
        //horizontalLayout.getStyle().set("align-content", "flex-start");
        //horizontalLayout.setSpacing(false);
        return layout;
    }
}
