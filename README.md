# Selenium Testing

## What is Selenium?

Selenium is a suite of tools for automating web browsers across many platforms. It is used for end-to-end testing of web
applications across different browsers, and can be integrated into continuous integration systems.

It uses the WebDriver API to remotely control browser instances and emulate a user’s interaction with the application.
When we run the test, a browser window will open and the test will be executed in that browser as if a human was
interacting with it.

We can test that the application creates the correct HTML elements, that the elements are displayed in the correct
order, that the elements have the correct content, and that the elements have the correct CSS classes. We can also
simulate the user clicking on buttons and links, and entering text into input fields.

## Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.6.0</version>
    </dependency>

    <dependency>
        <groupId>io.github.bonigarcia</groupId>
        <artifactId>webdrivermanager</artifactId>
        <version>5.3.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Web driver setup

Selenium requires a web driver to control the browser. The web driver is a separate executable that Selenium uses to
control the browser. The web driver for each browser needs to be installed separately.

In order to create the web driver, we use the WebDriverManager library which will set up the web driver for different
browsers automatically.

Next, we will set the timeout for the web driver to wait for the test to complete before failing it.

```java
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
```

> Note: in order to ensure that each test runs independently, we close the browser after each test.

## Running the tests

After we have set up the web driver, we can run the tests. The tests will open a browser window and execute the test in
that browser.

### Find elements

Searching for elements in the page can be done with multiple functions.

For example, we can search for an element by its id:

```java
WebElement element = webDriver.findElement(By.id("id"));
```

Or we can search for an element by its CSS class:

```java
WebElement element = webDriver.findElement(By.className("mat-card"));
```

Or we can search for an element by its CSS selector:

```java
WebElement element = webDriver.findElement(By.cssSelector("div.mat-card"));
WebElement button = webDriver.findElement(By.cssSelector("button[aria-label='Icon-button with a menu']"));
```

Or we can search for an element by the HTML tag name:

```java
WebElement element = webDriver.findElement(By.tagName("input"));
```

Or we can search for an element by the position in the page using XPath:

```java
WebElement element = webDriver.findElement(By.xpath("/html/body/app-root/mat-toolbar/div/button[1]"));
```

### Clicking on elements

We can click on elements using the `click()` function:

```java
WebElement element = webDriver.findElement(By.id("id"));
element.click();
```

### Actions

We can also use the Actions class to perform more complex actions on elements.

For example, we can use the Actions class to simulate the user hovering over an element:

```java
WebElement element = webDriver.findElement(By.id("id"));
Actions actions = new Actions(webDriver);
actions.moveToElement(element).perform();
```

Or we can use the Actions class to simulate the user clicking on an element:

```java
WebElement element = webDriver.findElement(By.id("id"));
Actions actions = new Actions(webDriver);
actions.moveToElement(element).click().perform();
```

Or we can the Actions class to enter text into an input field:

```java
WebElement element = webDriver.findElement(By.id("id"));
Actions actions = new Actions(webDriver);
actions.sendKeys(filterInput, "text").perform();
```

### Other web driver functions

We can also use other functions to interact with the web driver.

For example, we can use the `get()` function to navigate to a page:

```java
webDriver.get("https://hiking-around-romania.web.app");
```

Or we can use the `getTitle()` function to get the title of the page:

```java
String title = webDriver.getTitle();
```

Or we can use the `getCurrentUrl()` function to get the URL of the page:

```java
String url = webDriver.getCurrentUrl();
```

## Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/en/)