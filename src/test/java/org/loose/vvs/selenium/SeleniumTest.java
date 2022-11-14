package org.loose.vvs.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testTitle() {
        webDriver.get("https://hiking-around-romania.web.app");

        assertEquals("HikingAroundRomania", webDriver.getTitle());
    }

    @Test
    public void test10CardsAreVisible() {
        webDriver.get("https://hiking-around-romania.web.app");

        List<WebElement> elements = webDriver.findElements(By.className("mat-card"));
        assertEquals(10, elements.size());

        for (WebElement element : elements) {
            assertNotNull(element.findElement(By.tagName("h2")));
            assertNotNull(element.getCssValue("background-image"));
        }
    }

    @Test
    public void testFilterEventsInputExistsInFutureEventsTab() {
        webDriver.get("https://hiking-around-romania.web.app");

        WebElement futureEventsTab = webDriver.findElement(By.id("mat-tab-label-0-1"));
        futureEventsTab.click();

        WebElement input = webDriver.findElement(By.tagName("input"));

        assertNotNull(input);
        assertEquals("search", input.getAttribute("type"));
        assertEquals("Search", input.getAttribute("data-placeholder"));
    }

    @Test
    public void testMoveToAboutUsPage() {
        webDriver.get("https://hiking-around-romania.web.app");

        WebElement threeDotsButton = webDriver.findElement(By.cssSelector("button[aria-label='Icon-button with a menu']"));
        threeDotsButton.click();
        WebElement aboutUsButton = webDriver.findElement(By.xpath("//*[contains(text(), 'About Us')]"));
        aboutUsButton.click();

        assertEquals("https://hiking-around-romania.web.app/about-us", webDriver.getCurrentUrl());
    }

    @Test
    public void testAboutUsPageCardHover() throws InterruptedException {
        webDriver.get("https://hiking-around-romania.web.app/about-us");
        WebElement card = webDriver.findElement(By.cssSelector("mat-card"));


        Actions actions = new Actions(webDriver);
        actions.moveToElement(card).perform();

//        uncomment to see the hover effect
//        Thread.sleep(5000);
    }

    @Test
    public void testLoginMouseClick() {
        webDriver.get("https://hiking-around-romania.web.app");

        WebElement loginButton = webDriver.findElement(By.xpath("/html/body/app-root/mat-toolbar/div/button[2]"));

        Actions actions = new Actions(webDriver);
        actions.click(loginButton).perform();

        assertEquals("https://hiking-around-romania.web.app/login", webDriver.getCurrentUrl());
    }

    @Test
    public void testRegionFilter() {
        webDriver.get("https://hiking-around-romania.web.app");

        WebElement filterInput = webDriver.findElement(By.tagName("input"));

        Actions actions = new Actions(webDriver);
        actions.sendKeys(filterInput, "Ma").perform();

        List<WebElement> elements = webDriver.findElements(By.className("mat-card"));
        assertEquals(4, elements.size());
    }
}
