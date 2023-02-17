package tests;

import common.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogEditTest {
    public static ChromeDriver driver= Utils.createDriver();

    @BeforeAll
    static void baseControl() {
        driver.get("http://162.14.74.192:8081/blog_edit.html");
    }
    /*
    检查博客编辑页是否可以正常打开
     */
    @Test
    @Order(1)
    void editPageLoadTest() throws IOException {
        driver.findElement(By.cssSelector("#title"));
        driver.findElement(By.cssSelector("body > div.blog-edit-container > div.title > button"));
        Utils.getScreenShot(getClass().getName());
    }
    /*
    检查能不能正常编辑并发布文章
     */
    @Test
    @Order(2)
    void editAndSubmitBlogTest() throws IOException, InterruptedException {
        String title="测试文章";
        driver.findElement(By.cssSelector("#title")).sendKeys(title);
        // 因博客系统使用到的编辑器是第三方库，所以不能直接使用sendKeys向编辑模块发送文本
        driver.findElement(By.cssSelector("#editorDiv > div.editormd-toolbar > div > ul > li:nth-child(20) > a > i")).click();
        driver.findElement(By.cssSelector("#editorDiv > div.editormd-toolbar > div > ul > li:nth-child(21) > a > i")).click();
        driver.findElement(By.cssSelector("body > div.blog-edit-container > div.title > button")).click();
        Thread.sleep(500);
        // 对异常登录结果进行检测
        Alert alert=driver.switchTo().alert();
        String expect="恭喜：发布成功！";
        Assertions.assertEquals(expect,alert.getText());
        alert.accept();
        Utils.getScreenShot(getClass().getName());
    }
}
