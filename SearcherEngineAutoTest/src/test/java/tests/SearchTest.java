package tests;

import common.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchTest {
    public static ChromeDriver driver= Utils.createDriver();
    // 先获取到driver对象，然后再访问URL
    @BeforeAll
    static void baseControl() {
        driver.get("http://162.14.74.192:8080/index.html");
    }

    /*
    检查搜索打开页面是否正确
    检查点：搜索框 搜索按钮是否存在
     */
    @Test
    @Order(1)
    void loadTest() throws IOException {
        driver.findElement(By.cssSelector("#search-key"));
        driver.findElement(By.cssSelector("#search-btn"));
        Utils.getScreenShot(getClass().getName());
    }

    /*
    多个参数检查搜索结果是否能加载成功
     */
    @ParameterizedTest
    @ValueSource(strings = {"array","list"})
    void searchTest(String text) throws IOException {
        driver.findElement(By.cssSelector("#search-key")).clear();
        driver.findElement(By.cssSelector("#search-key")).sendKeys(text);

        driver.findElement(By.cssSelector("#search-btn")).click();
        driver.findElement(By.cssSelector("body > div > div.result > div:nth-child(2) > a"));
        Utils.getScreenShot(getClass().getName());
    }
    @AfterAll
    static void driverQuit() {
        driver.quit();
    }
}
