package org.loose.vvs.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SeleniumTest {

    private WebDriver webDriver;

    private static WebDriverManager webDriverManager;

    @BeforeAll
    static void beforeAll() {
        if (WebDriverManager.chromedriver().getBrowserPath().isPresent()) {
            webDriverManager = WebDriverManager.chromedriver();
            return;
        }
        if (WebDriverManager.firefoxdriver().getBrowserPath().isPresent()) {
            webDriverManager = WebDriverManager.firefoxdriver();
            return;
        }
        if (WebDriverManager.edgedriver().getBrowserPath().isPresent()) {
            webDriverManager = WebDriverManager.edgedriver();
            return;
        }
        if (WebDriverManager.operadriver().getBrowserPath().isPresent()) {
            webDriverManager = WebDriverManager.operadriver();
            return;
        }
        if (WebDriverManager.safaridriver().getBrowserPath().isPresent())
            webDriverManager = WebDriverManager.safaridriver();
    }

    @BeforeEach
    public void setup() {
        assertNotNull(webDriverManager);
        webDriverManager.setup();
        webDriver = webDriverManager.create();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(5, ChronoUnit.SECONDS));
    }

    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

    @Test
    public void test1() {
        webDriver.get("https://dxworks.org");

        System.out.println(webDriver.getTitle());
    }
}
