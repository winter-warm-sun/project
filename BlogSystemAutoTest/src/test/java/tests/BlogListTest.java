package tests;

import common.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class BlogListTest {
    public static ChromeDriver driver= Utils.createDriver();

    @BeforeAll
    static void baseControl() {
        driver.get("http://162.14.74.192:8081/myblog_list.html");
    }

    /**
     * 博客列表页可以正常显示
     */
    @Test
    void listPageLoadTest() throws IOException {
        driver.findElement(By.cssSelector("body > div.nav > a:nth-child(5)"));
        driver.findElement(By.cssSelector("body > div.nav > a:nth-child(6)"));
        Utils.getScreenShot(getClass().getName());
    }
}
