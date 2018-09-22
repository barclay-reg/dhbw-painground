package net.kleinschmager.dhbw.tfe16.painground.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Cursor;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import net.kleinschmager.dhbw.tfe16.painground.persistence.model.Level;
import net.kleinschmager.dhbw.tfe16.painground.persistence.model.Skill;
import net.kleinschmager.dhbw.tfe16.painground.persistence.repository.MemberProfileRepository;
import net.kleinschmager.dhbw.tfe16.painground.persistence.repository.SkillRepository;
import net.kleinschmager.dhbw.tfe16.painground.ui.components.DashboardCounter;
import net.kleinschmager.dhbw.tfe16.painground.ui.components.FuseCard;

@Route("dashboard")
@Theme(Lumo.class)
public class DashboardView extends Div {

	private SkillRepository skillRepo;
	private MemberProfileRepository profileRepo;

	public DashboardView(@Autowired SkillRepository skillRepository,
			@Autowired MemberProfileRepository profileRepository) {

		this.skillRepo = skillRepository;
		this.profileRepo = profileRepository;
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {

		Board board = new Board();

		long overallSkills = skillRepo.count();
		Skill exampleExpertSkill = new Skill();
		exampleExpertSkill.setLevel(Level.EXPERT);
		long expertSkills = skillRepo.count(Example.of(exampleExpertSkill));

		long overallProfiles = profileRepo.count();

		DashboardCounter overallSkillCounter = new DashboardCounter("Skills", "insgesamt",
				Integer.valueOf((int) overallSkills));
		overallSkillCounter.getElement().getStyle().set("color", "lightgreen");
		DashboardCounter expertSkillsCounter = new DashboardCounter("Expert Skills", "",
				Integer.valueOf((int) expertSkills));
		expertSkillsCounter.getElement().getStyle().set("color", "lightblue");
		DashboardCounter overallProfilesCounter = new DashboardCounter("Profile", "",
				Integer.valueOf((int) overallProfiles));
		overallProfilesCounter.getElement().getStyle().set("color", "red");

		board.addRow(
				new FuseCard(overallProfilesCounter), 
				new FuseCard(overallSkillCounter), 
				new FuseCard(expertSkillsCounter));
			
		
		Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Most used skills");

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        tooltip.setPointFormat("{series.name}: <b>{point.percentage}%</b>");
        conf.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("java", 60.0));
        series.add(new DataSeriesItem("xml", 29.0));
        DataSeriesItem chrome = new DataSeriesItem("ood", 21.0);
        chrome.setSliced(true);
        chrome.setSelected(true);
        series.add(chrome);
        
        conf.setSeries(series);
        chart.setVisibilityTogglingDisabled(true);
		
		board.addRow(chart);
		

		NavigationLayout navLayout = new NavigationLayout();

		add(navLayout.getNavigationLayout(board));
	}
}
