package tests;

import common.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogLoginTest {
    public static ChromeDriver driver= Utils.createDriver();
    // 如果要测试登录页面，以下所有的用例都有一个共同的步骤
    // 1、要有浏览器对象  2、访问登录页面的URL
    @BeforeAll
    static void baseControl() {
        driver.get("http://162.14.74.192:8081/login.html");
    }

    /*
    检查登录页面打开是否正确
    检查点：公共主页 注册元素是否存在
     */
    @Test
    @Order(1)
    void loginLoad() throws IOException {
        driver.findElement(By.cssSelector("body > div.nav > a:nth-child(4)"));
        driver.findElement(By.cssSelector("body > div.nav > a:nth-child(5)"));
        Utils.getScreenShot(getClass().getName());
    }

    /*
    检查正常登录情况
     */
    @ParameterizedTest
    @CsvSource({"admin,admin","zhangsan,123"})
    @Order(2)
    void loginSuccess(String name,String password) throws IOException, InterruptedException {
        driver.findElement(By.cssSelector("#username")).clear();
        driver.findElement(By.cssSelector("#password")).clear();

        driver.findElement(By.cssSelector("#username")).sendKeys(name);
        driver.findElement(By.cssSelector("#password")).sendKeys(password);
        driver.findElement(By.cssSelector("#submit")).click();
        Thread.sleep(500);
        // 对登陆结果进行检测，
        Alert alert=driver.switchTo().alert();
        String expect="登录成功！";
        Assertions.assertEquals(expect,alert.getText());
        alert.accept();
        Utils.getScreenShot(getClass().getName());
        driver.navigate().back();
    }

    /*
    检查异常登录情况
     */
    @ParameterizedTest
    @CsvSource({"admin,123"})
    @Order(3)
    void loginFail(String name,String password) throws InterruptedException, IOException {
        driver.findElement(By.cssSelector("#username")).clear();
        driver.findElement(By.cssSelector("#password")).clear();

        driver.findElement(By.cssSelector("#username")).sendKeys(name);
        driver.findElement(By.cssSelector("#password")).sendKeys(password);
        driver.findElement(By.cssSelector("#submit")).click();
        Thread.sleep(500);
        // 对异常登录结果进行检测
        Alert alert=driver.switchTo().alert();
        String expect="抱歉：用户名或密码错误，请重新输入！";
        Assertions.assertEquals(expect,alert.getText());
        alert.accept();
        Utils.getScreenShot(getClass().getName());
    }
}
