/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.ui;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.configuration.ConfigurationProperties.TriggerMode;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe15.painground.persistence.repository.MemberProfileRepository;
import net.kleinschmager.dhbw.tfe15.painground.ui.pages.MemberProfilePage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FluentConfiguration(
		screenshotMode = TriggerMode.AUTOMATIC_ON_FAIL, 
		screenshotPath = "target/ui-test-screenshots/")
public class MainUiTest extends FluentTest {

	private static final String TEST_SURNAME_2 = "Surname 2";
	private static final String TEST_SURNAME_1 = "Surname 1";
	private static final MemberProfile TEST_ITEM_1 = new MemberProfile("testId1", TEST_SURNAME_1);
	private static final MemberProfile TEST_ITEM_2 = new MemberProfile("testId2", TEST_SURNAME_2);

	private String WAIT_FOR_VAADIN_SCRIPT = "if (window.vaadin == null) {" + "  return true;" + "}"
			+ "var clients = window.vaadin.clients;" + "if (clients) {" + "  for (var client in clients) {"
			+ "    if (clients[client].isActive()) {" + "      return false;" + "    }" + "  }" + "  return true;"
			+ "} else {" +
			// A Vaadin connector was found so this is most likely a Vaadin
			// application. Keep waiting.
			"  return false;" + "}";

	@LocalServerPort
	int randomPort;
	
	// private PhantomJSDriver webDriver = new PhantomJSDriver();
	private ChromeDriver webDriver;

	@Autowired
	MemberProfileRepository repository;

	@Page
	MemberProfilePage memberProfilePage;
	
	@Override
	public WebDriver newWebDriver() {

		final ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");

		final DesiredCapabilities dc = new DesiredCapabilities();
		dc.setJavascriptEnabled(true);
		dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		webDriver = new ChromeDriver(dc);
		webDriver.manage().window().setSize(new Dimension(1280,1024));
		return webDriver;
	}
	
	@Override
	public String getBaseUrl() {
		return "http://localhost:" + randomPort;
	}

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
		
		TEST_ITEM_1.setGivenName("Givenname 1");
		TEST_ITEM_1.setDateOfBirth(Date.from(LocalDate.of(1999, 7, 17).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		TEST_ITEM_2.setGivenName("Givenname 2");
		TEST_ITEM_2.setDateOfBirth(Date.from(LocalDate.of(2003, 10, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
	}

	@Before
	public void setUp() {
		repository.deleteAll();
		repository.save(Arrays.asList(TEST_ITEM_1, TEST_ITEM_2));
		repository.flush();
	}

	@Test
	public void loadDefaultPageAndMakeScreenshot() throws IOException {
		goTo(getBaseUrl());
		waitForVaadin();
		
		takeScreenShot("screenshot_testcase_loadDefaultPageAndMakeScreenshot.png");
	}
	
	@Test
	public void testGridSize() throws IOException {
	
		// GIVEN
		// WHEN
		goTo(memberProfilePage);
		waitForVaadin();
		// THEN
		assertEquals("Grid row count should be match", 2, memberProfilePage.getGridRowCount());
		assertEquals("Grid column count should match", 7, memberProfilePage.getGridColumnCount());
	}
	
	@Test
	public void testGridSorting() throws IOException {
		
		// GIVEN
		// WHEN
		goTo(getBaseUrl());
		waitForVaadin();
		
		goTo(memberProfilePage);
		waitForVaadin();
		
		
		memberProfilePage.clickColumnHeaderSurname();
		waitForVaadin();
		List<String> firstRowBefore = memberProfilePage.getRowContent(0);
		
		memberProfilePage.clickColumnHeaderSurname();
		waitForVaadin();
		List<String> firstRowAfter = memberProfilePage.getRowContent(0);
		
		// THEN
		assertThat(TEST_SURNAME_1, equalTo(firstRowBefore.get(2)));
		assertThat(TEST_SURNAME_2, equalTo(firstRowAfter.get(2)));
	}
	

	/**
	 * Block until Vaadin reports it has finished processing server messages.
	 */
	private void waitForVaadin() {

		long timeoutTime = System.currentTimeMillis() + 20000;
		Boolean finished = false;
		while (System.currentTimeMillis() < timeoutTime && !finished) {
			finished = (Boolean) ((JavascriptExecutor) webDriver).executeScript(WAIT_FOR_VAADIN_SCRIPT);
			if (finished == null) {
				finished = false;
			}
		}
	}

}
