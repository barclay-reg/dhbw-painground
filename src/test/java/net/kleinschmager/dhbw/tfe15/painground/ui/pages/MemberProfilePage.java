package net.kleinschmager.dhbw.tfe15.painground.ui.pages;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import net.kleinschmager.dhbw.tfe15.painground.ui.views.MemberProfileList;

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

	@FindBy(css = "#" + MemberProfileList.MAIN_COMPONENT_ID + " > div.v-grid-tablewrapper > table > thead.v-grid-header > tr.v-grid-row > th.v-grid-cell")
    private FluentList<FluentWebElement> gridHeaderCells;
	
	@FindBy(css = "#" + MemberProfileList.MAIN_COMPONENT_ID + " > div.v-grid-tablewrapper > table > tbody.v-grid-body > tr.v-grid-row")
    private FluentList<FluentWebElement> gridRows;
	
	public void clickColumnHeaderSurname() {
		
		FluentList<FluentWebElement> columnHeader = gridHeaderCells.$("div.v-grid-column-header-content", withText("Sur Name"));
		columnHeader.click();
	}
	
	public int getGridColumnCount() {
		
		return gridHeaderCells.count();
	}
	
	public int getGridRowCount() {
		
		return gridRows.count();
	}
	
	public void clickColumnHeaderGivenname() {
		FluentList<FluentWebElement> columnHeader = gridHeaderCells.$("div.v-grid-column-header-content", withText("Given Name"));
		columnHeader.click();
	}
	
	public List<String> getRowContent(int rowIndex) {
		
		FluentList<FluentWebElement> cellsInRow = gridRows.index(rowIndex).$("td.v-grid-cell");
		
		return cellsInRow
				.stream()
				.map(cell -> cell.text())
				.collect(Collectors.toList());
	}
	
	public List<String> getFirstRow() {
		
		return null;
	}
	
}
