package net.kleinschmager.dhbw.tfe16.painground.ui;

import static com.github.appreciated.app.layout.entity.Section.HEADER;

import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.MenuHeaderComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.design.AppLayoutDesign;
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.notification.entitiy.DefaultNotification;
import com.github.appreciated.app.layout.notification.entitiy.Priority;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

/**
 * The main view as target/routerlayout for all other views
 */
@Theme(Material.class)
@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainView extends AppLayoutRouterLayout {
   
    private Behaviour variant;   
    DefaultNotificationHolder notifications;
    DefaultBadgeHolder badge;
    private Thread currentThread;

    @Override
    public AppLayout getAppLayout() {
        if (variant == null) {
            variant = Behaviour.LEFT_HYBRID;
            notifications = new DefaultNotificationHolder(newStatus -> {
            });
            badge = new DefaultBadgeHolder();
        }

            return AppLayoutBuilder.get(variant)
                    .withTitle("App Layout")
                    .withAppBar(
                            AppBarBuilder.get().add(new AppBarNotificationButton(VaadinIcon.BELL, notifications)).build())
                    .withDesign(AppLayoutDesign.MATERIAL)
                    .withAppMenu(
                            LeftAppMenuBuilder.get()
                                    .addToSection(new MenuHeaderComponent("PainGround", "Version 0.0.1", "frontend/images/dont-panic-alpha.png"), HEADER)
                                    //.addToSection(new LeftClickableComponent("Set Behaviour HEADER", VaadinIcon.COG.create(), clickEvent -> openModeSelector(variant)), HEADER)
                                    .add(new LeftNavigationComponent("Home", VaadinIcon.HOME.create(), AllProfilesView.class))
                                    .add(new LeftNavigationComponent("Add Profile", VaadinIcon.PLUS.create(), AddProfileView.class))
                                    .build()
                    ).build();
    }
}