package tests;

import common.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class BlogDetailTest {
    public static ChromeDriver driver= Utils.createDriver();
    @BeforeAll
    static void baseControl() {
        driver.get("http://162.14.74.192:8081/blog_content.html?blogId=1");
    }

    @Test
    void blogDetailLoadTest() throws IOException {
        driver.findElement(By.xpath("//*[@id=\"title\"]"));
        Utils.getScreenShot(getClass().getName());
    }
}
