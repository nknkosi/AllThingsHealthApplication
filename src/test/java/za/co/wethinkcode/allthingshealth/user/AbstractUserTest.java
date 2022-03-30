package za.co.wethinkcode.allthingshealth.user;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.javalin.Javalin;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import za.co.wethinkcode.allthingshealth.Server;
import za.co.wethinkcode.allthingshealth.util.Strings;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base class that sets up the browser driver and has common user steps.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractUserTest {
    /**
     * The web server under test
     */
    protected Javalin app = Server.getInstance();

    /**
     * The base url of the web server that was spun up
     */
    protected String baseUrl;

    /**
     * The browser that we will drive to test the app
     */
    protected ChromeDriver driver;

    /**
     * Before all tests, start ther server and capture the base url of the server
     */
    @BeforeAll
    void startServer(){
        app.start(0);
        baseUrl = "http://localhost:" + app.port();
   }

    /**
     * Before each test, configure the browser
     */
    @BeforeEach
    void start() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // necessary for grading environment
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
    }

    /**
     * After all tests, stop the web server
     */
    @AfterAll
    void stopServer(){
        app.close();
    }

    /**
     * Navigate to a specific page
     * @param pageUrl The page must be prefixed with '/' (e.g. "/home")
     */
    protected void navigateTo(String pageUrl) {
        driver.get(appUrl(pageUrl));
    }

    /**
     * Get the full url of a page
     * @param pageUrl The page must be prefixed with '/' (e.g. "/home")
     */
    protected String appUrl(String pageUrl) {
        return baseUrl + pageUrl;
    }

    /**
     * Perform a login and verify that the home page was loaded
     * @param email the email address of the user logging in
     */
    protected void doLogin(String email) {
        navigateTo("/index.html");

        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("submit")).click();

        assertThat(driver.getCurrentUrl()).isEqualToIgnoringCase(appUrl("/home"));
        assertThat(driver.findElement(By.id("user")).getText()).isEqualTo(email);
        assertThat(driver.findElement(By.id("logout")).getText())
                .isEqualTo("Logout " + Strings.capitaliseFirstLetter(email.split("@")[0]));
    }

    /**
     * Submit a form that has a button with id of 'submit'
     */
    protected void submitForm() {
        driver.findElement(By.id("submit")).submit();
    }

    /**
     * Fill a field on a form
     * @param id the css id of the field
     * @param keys the keystrokes (value) to fill in
     */
    protected void fillField(String id, String keys) {
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(keys);
    }

}
