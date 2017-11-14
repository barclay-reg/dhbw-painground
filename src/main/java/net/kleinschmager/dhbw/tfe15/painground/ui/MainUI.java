/**
 * copyright by Robert Kleinschmager
 */
package net.kleinschmager.dhbw.tfe15.painground.ui;

import static com.github.appreciated.app.layout.builder.AppLayoutBuilder.Position.HEADER;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.builder.design.AppBarDesign;
import com.github.appreciated.app.layout.builder.entities.NavigationElementInfo;
import com.github.appreciated.app.layout.builder.providers.DefaultSpringNavigationElementInfoProvider;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import net.kleinschmager.dhbw.tfe15.painground.ui.views.MemberProfileList;

/**
 * Entry Point to the UI, describing the {@link AppLayout} frame and wire the
 * {@link SpringNavigator} into the menu, which is rendered by {@link AppLayout}
 * 
 * @author robertkleinschmager
 *
 */
@SpringUI
@SpringViewDisplay
@Theme("paintheme")
@Viewport("initial-scale=1, maximum-scale=1")
@Title("DHBW Painground - Peoples Knowledge")
@Push
public class MainUI extends UI {

	/**
	 * the mainContent of this UI 
	 */
	private VerticalLayout mainContent;

	@Autowired
	SpringNavigator springNavigator;

	@Value("${painground.app.version}")
	private String applicationVersion;

	@Override
	protected void init(VaadinRequest request) {

		mainContent = new VerticalLayout();
		mainContent.setMargin(false);
		mainContent.setSizeFull();

		setAppLayout();
		
		setContent(mainContent);

	} // end init method

	private void setAppLayout() {
		mainContent.removeAllComponents();

		AppLayout appLayout = AppLayoutBuilder.get(Behaviour.LEFT_RESPONSIVE_HYBRID)
				// needed to tell springNavigator, where to render the views
				.withCDI(true)
                .withNavigationElementInfoProvider(new DefaultSpringNavigationElementInfoProvider())
                .withNavigatorProducer(panel -> {
					springNavigator.init(this, panel);
					return springNavigator;
				})
                .withTitle("Peoples Knowledge")
				.withDesign(AppBarDesign.MATERIAL)
				.add(new MenuHeader("PainGround", "Version " + applicationVersion,
						new ThemeResource("images/dont-panic-alpha.png")), HEADER)				
				// start defining the contained views, first will be loaded on start
				.add(MemberProfileList.class)
				.build();
		mainContent.addComponent(appLayout);
	} // end setAppLayout method
}// end class
