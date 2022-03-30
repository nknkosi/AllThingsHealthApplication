package za.co.wethinkcode.allthingshealth.user;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class LogoutTest extends AbstractUserTest {
    @Test
    void logout() {
        doLogin("nkosi@gmail.com");

        driver.findElement(By.id("logout")).click();
        assertThat(driver.getCurrentUrl()).isEqualToIgnoringCase(appUrl("/index.html"));
    }
}
