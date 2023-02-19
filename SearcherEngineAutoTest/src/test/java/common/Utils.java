package common;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static ChromeDriver driver;

    // 创建驱动对象
    public static ChromeDriver createDriver() {
        // 设置无头模式
        ChromeOptions options=new ChromeOptions();
        options.addArguments("-headless");
        // 驱动对象已经创建好了/没有创建
        if(driver==null) {
            driver=new ChromeDriver(options);
            // 创建隐式等待(防止因页面加载过慢而导致错误)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    public static List<String> getTime() {
        // 文件按照天的维度按文件夹进行保存
        SimpleDateFormat sim1=new SimpleDateFormat("yyyyMMdd-HHmmssSS");
        SimpleDateFormat sim2=new SimpleDateFormat("yyyyMMdd");
        String fileName=sim1.format(System.currentTimeMillis());
        String dirName=sim2.format(System.currentTimeMillis());
        List<String> list=new ArrayList<>();
        list.add(dirName);
        list.add(fileName);
        return list;
    }
    /**
     * 获取屏幕截图，把所有的用例执行结果保存下来
     */
    public static void getScreenShot(String str) throws IOException {
        List<String> list=getTime();
        String fileName="./src/test/java/screenshot/"+list.get(0)+"/"+str+"_"+list.get(1)+".png";
        File srcFile=driver.getScreenshotAs(OutputType.FILE);
        // 把屏幕截图生成的文件放到指定的路径
        FileUtils.copyFile(srcFile,new File(fileName));
    }
}
