package tests;

import common.Utils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverQuitTest {
    public static ChromeDriver driver= Utils.createDriver();

    // 最后需要关闭driver
    @Test
    void driverQuit() {
        driver.quit();
    }
}
