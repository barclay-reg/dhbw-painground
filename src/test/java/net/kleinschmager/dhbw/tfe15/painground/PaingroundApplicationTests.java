package net.kleinschmager.dhbw.tfe15.painground;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.configuration.ConfigurationProperties.TriggerMode;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe15.painground.persistence.repository.MemberProfileRepository;

/**
 * tests the application
 * 
 * @author robertkleinschmager
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FluentConfiguration(screenshotMode = TriggerMode.AUTOMATIC_ON_FAIL, screenshotPath = "target/ui-test-failures/")
public class PaingroundApplicationTests extends FluentTest {

	private static final MemberProfile TEST_ITEM_1 = new MemberProfile("testId1", "test Surname 1");

	private String WAIT_FOR_VAADIN_SCRIPT = "if (window.vaadin == null) {" + "  return true;" + "}"
			+ "var clients = window.vaadin.clients;" + "if (clients) {" + "  for (var client in clients) {"
			+ "    if (clients[client].isActive()) {" + "      return false;" + "    }" + "  }" + "  return true;"
			+ "} else {" +
			// A Vaadin connector was found so this is most likely a Vaadin
			// application. Keep waiting.
			"  return false;" + "}";

	@LocalServerPort
	int randomPort;

	@Autowired
	MemberProfileRepository repository;

	// private PhantomJSDriver webDriver = new PhantomJSDriver();
	private ChromeDriver webDriver;

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

	private String getUrlOfEmbeddedServer() {
		return "http://localhost:" + randomPort;
	}

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
	}

	@Before
	public void setUp() {
		repository.deleteAll();
		repository.save(Arrays.asList(TEST_ITEM_1));
		repository.flush();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void loadPageAndMakeScreenshot() throws IOException {
		goTo(getUrlOfEmbeddedServer());
		await().atMost(10, TimeUnit.SECONDS).until($(".v-app-loading")).not().present();
		// await().atMost(60,
		// TimeUnit.SECONDS).until($(".v-loading-indicator")).not().present();
		waitForVaadin();

		File screenshot = webDriver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File("target/screenshot_testcase_loadPageAndMakeScreenshot.png"));
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
