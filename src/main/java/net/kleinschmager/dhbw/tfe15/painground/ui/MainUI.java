/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.builder.design.AppBarDesign;
import com.github.appreciated.app.layout.builder.entities.NavigationElementInfo;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.github.appreciated.app.layout.interceptor.DefaultViewNameInterceptor;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe15.painground.persistence.repository.MemberProfileRepository;
import net.kleinschmager.dhbw.tfe15.painground.ui.views.MemberProfileList;

import static com.github.appreciated.app.layout.builder.AppLayoutBuilder.Position.HEADER;

/**
 * @author robertkleinschmager
 *
 */
@SpringUI
@SpringViewDisplay
@Theme("paintheme")
@Viewport("initial-scale=1, maximum-scale=1")
@Title("DHBW Painground - PeopleSkill")
@Push
public class MainUI extends UI {

	
	private VerticalLayout holder;
	
	@Autowired
	SpringNavigator springNavigator;
	
		
	@Override
	protected void init(VaadinRequest request) {
		
		holder = new VerticalLayout();
        holder.setMargin(false);
        setAppLayout(Behaviour.LEFT);
        setContent(holder);
        holder.setSizeFull();

	}
	
	private void setAppLayout(Behaviour variant) {
        holder.removeAllComponents();


        AppLayout appLayout = AppLayoutBuilder.get()
                .withBehaviour(Behaviour.LEFT_RESPONSIVE_HYBRID)
                .withTitle("Peoples Knowledge")
                // needed to tell springNavigator, where to render the views
                .withNavigatorProducer(panel -> {
                		springNavigator.init(this, panel);
                		return springNavigator;
                } )
                .withDesign(AppBarDesign.MATERIAL)
                .add(new MenuHeader("PainGround", "Version 0.9.8", new ThemeResource("images/dont-panic-alpha.png")), HEADER)
                // needed to provide the Caption and ViewName
                .withNavigationElementInfoProvider( clazz -> {
                		if (MemberProfileList.class.equals(clazz)) {
                			return new NavigationElementInfo("Profiles", MemberProfileList.VIEW_NAME);                			
                		} else {
                			throw new IllegalArgumentException("You need to define a NavigatinElementInfo for view: "+ clazz.getName());
                		}
                			
                })
                .add(MemberProfileList.class, VaadinIcons.HOME)
                .withDefaultNavigationView(MemberProfileList.class)
                .build();
        holder.addComponent(appLayout);
    }
}
