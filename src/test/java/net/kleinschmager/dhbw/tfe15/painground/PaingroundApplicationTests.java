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
@SpringBootTest
public class PaingroundApplicationTests {

	@Test
	public void contextLoads() {
	}

}