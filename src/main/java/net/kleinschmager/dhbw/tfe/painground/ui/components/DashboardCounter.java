package net.kleinschmager.dhbw.tfe.painground.ui.components;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("dashboard-counter")
@HtmlImport("src/dashboard-counter.html")
public class DashboardCounter extends PolymerTemplate<DashboardCounterBindingModel> {

	public DashboardCounter(String title, String subtitle, Integer counter) {
		getModel().setTitle(title);
		getModel().setSubTitle(subtitle);
		getModel().setCountDigit(counter);
	}
}
