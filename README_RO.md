# Selenium Testing

## Ce este Selenium?

Selenium este un set de instrumente pentru automatizarea browserelor web pe mai multe platforme. Este folosit pentru
testarea aplicatiilor web pe diferite browsere, si poate fi integrat in sisteme de integrare continua.

Se foloseste API-ul WebDriver pentru a controla remote browser instances si pentru a emula o interactiune a unui
utilizator cu aplicatia. Cand rulam testul, o fereastra de browser se va deschide si testul va fi executat in browser-ul
respectiv ca si cum un utilizator ar interactiona cu el.

Putem testa ca aplicatia creaza elementele HTML corecte, ca elementele sunt afisate in ordinea corecta, ca elementele au
continutul corect, si ca elementele au clasele CSS corecte. Putem simula si utilizatorul care apasa butoane si link-uri,
si care introduce text in campurile de input.

## Dependinte

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

## Setup-ul web driver-ului

Selenium necesita un web driver pentru a controla browser-ul. Web driver-ul este un executabil separat pe care Selenium
il foloseste pentru a controla browser-ul. Web driver-ul pentru fiecare browser trebuie instalat separat.

Pentru a crea web driver-ul, folosim biblioteca WebDriverManager care va seta web driver-ul pentru browsere diferite
automat.

In continuare, vom seta timeout-ul pentru web driver-ul pentru a astepta ca testul sa se termine inainte de a-l esua.

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

> Observatie: pentru a asigura ca fiecare test ruleaza independent, inchidem browser-ul dupa fiecare test.

## Rularea testelor

Dupa ce am configurat web driver-ul, putem rula testele. Testele vor deschide o fereastra de browser si vor executa
testul in browser-ul respectiv.

### Cautarea elementelor

Cautarea elementelor in pagina se poate face cu mai multe functii.

De exemplu, putem cauta un element dupa id-ul sau:

```java
WebElement element = webDriver.findElement(By.id("id"));
```

Sau putem cauta un element dupa clasa CSS a acestuia:

```java
WebElement element = webDriver.findElement(By.className("mat-card"));
```

Sa cautam un element dupa selectorul CSS:

```java
WebElement element = webDriver.findElement(By.cssSelector("div.mat-card"));
WebElement button = webDriver.findElement(By.cssSelector("button[aria-label='Icon-button with a menu']"));
```

Sau putem cauta un element dupa numele tag-ului HTML:

```java
WebElement element = webDriver.findElement(By.tagName("input"));
```

Sau putem cauta un element dupa pozitia sa in pagina folosind XPath:

```java
WebElement element = webDriver.findElement(By.xpath("/html/body/app-root/mat-toolbar/div/button[1]"));
```

### Apasarea elementelor (click)

Putem apasa pe elemente folosind functia `click()`:

```java
WebElement element = webDriver.findElement(By.id("id"));
element.click();
```

### Actiuni

Putem folosi clasa Actions pentru a executa actiuni mai complexe pe elemente.

De exemplu, putem folosi clasa Actions pentru a simula utilizatorul care trece cu mouse-ul peste un element (hover):

```java
WebElement element = webDriver.findElement(By.id("id"));
Actions actions = new Actions(webDriver);
actions.moveToElement(element).perform();
```

Sau putem folosi clasa Actions pentru a simula utilizatorul care apasa pe un element:

```java
WebElement element = webDriver.findElement(By.id("id"));
Actions actions = new Actions(webDriver);
actions.moveToElement(element).click().perform();
```

Sau puteam folosi clasa Actions pentru a introduce text in campul de input:

```java
WebElement element = webDriver.findElement(By.id("id"));
Actions actions = new Actions(webDriver);
actions.sendKeys(filterInput, "text").perform();
```

### Alte functii ale web driver-ului

Putem folosi si alte functii pentru a interactiona cu web driver-ul.

De exemplu, putem folosi functia `get()` pentru a naviga la o pagina:

```java
webDriver.get("https://hiking-around-romania.web.app");
```

Sau putem folosi functia `getTitle()` pentru a obtine titlul paginii:

```java
String title = webDriver.getTitle();
```

Sau putem folosi functia `getCurrentUrl()` pentru a obtine URL-ul paginii:

```java
String url = webDriver.getCurrentUrl();
```

## Resurse

- [Documentatie Selenium](https://www.selenium.dev/documentation/en/)