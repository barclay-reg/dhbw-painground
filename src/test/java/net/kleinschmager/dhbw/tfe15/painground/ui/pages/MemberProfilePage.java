package net.kleinschmager.dhbw.tfe15.painground.ui.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import net.kleinschmager.dhbw.tfe15.painground.ui.views.MemberProfileList;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import java.util.List;

/**
 * Page (in the meaning of Page Object Pattern) for the Page, which shows the list of all MemberProfiles
 * 
 * @author Robert Kleinschmager
 *
 * @see MemberProfileList
 * 
 * @see https://github.com/FluentLenium/FluentLenium/blob/develop/docs/docs.md#pages
 * @see https://github.com/SeleniumHQ/selenium/wiki/PageObjects
 */
@PageUrl("/#!" + MemberProfileList.VIEW_NAME)
@FindBy(css="#" + MemberProfileList.MAIN_COMPONENT_ID)
public class MemberProfilePage extends FluentPage {

	@FindBy(css = "#" + MemberProfileList.MAIN_COMPONENT_ID + "")
    private FluentWebElement columnHeaderSurname;
	
	public void clickColumnHeaderSurname() {
		FluentList<FluentWebElement> columnHeader = 
				$("#" + MemberProfileList.MAIN_COMPONENT_ID)
				.$("div.grid-table-wrapper > table > thead.v-grid-header > tr.v-grid.row")
				.$("th.v-grid-cell > div.v-grid-column-header-content", withName("Sur Name"));
		columnHeader.click();
	}
	
	public void clickColumnHeaderGivenname() {
		FluentList<FluentWebElement> columnHeader = $("#" + MemberProfileList.MAIN_COMPONENT_ID)
				.$("div.grid-table-wrapper > table > thead.v-grid-header > tr.v-grid.row")
				.$("th.v-grid-cell > div.v-grid-column-header-content", withName("Sur Name"));
		columnHeader.click();
	}
	
	public List<String> getFirstRow() {
		
		return null;
	}
	
}
