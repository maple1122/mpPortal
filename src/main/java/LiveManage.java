import base.CommonMethod;
import base.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/1/29 11:03
 */
public class LiveManage extends Login {
    static WebDriver driver;

    //开启或停用
    public static void openOrClose() throws InterruptedException {
        seachAuto();

        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@id='showData']/div"))) {
            List<WebElement> lives = driver.findElements(By.xpath("//div[@id='showData']/div"));
            String opp = lives.get(0).findElement(By.xpath("div[@class='btns']/span[1]")).getAttribute("class");
            lives.get(0).findElement(By.xpath("div[@class='btns']/span[1]")).click();
            if (opp.contains("open")) {
                System.out.println("~~~ openOrClose()，开启直播，执行成功 ~~~");
            } else System.out.println("~~~ openOrClose()，停用直播，执行成功 ~~~");
        } else System.out.println("没有达人直播测试数据");
        Thread.sleep(3000);
    }

    //直播下线
    public static void unpublish() throws InterruptedException {
        recordList(driver);

        for (int i = 0; i < 50; i++) {
            if (CommonMethod.isJudgingElement(driver, By.id("layui-layer-iframe" + i))) {
                driver.switchTo().frame("layui-layer-iframe" + i);
                break;
            }
        }
        Thread.sleep(1000);
        List<WebElement> lives = driver.findElements(By.xpath("//div[@class='show-content-data']/div"));
        if (lives.size() > 0)
            for (int i = 0; i < lives.size(); i++) {
                if (lives.get(i).findElement(By.xpath("div[@class='btns']/span[1]")).getAttribute("class").contains("hidden")) {
                    lives.get(i).findElement(By.xpath("div[@class='btns']/span[@class='btn btn-unpublish']")).click();
                    driver.findElement(By.className("layui-layer-btn0")).click();
                    Thread.sleep(500);
                    driver.findElement(By.className("lz-opt-text")).click();
                    System.out.println("~~~ unpublish()，下线直播，执行成功 ~~~");
                    break;
                }
            }
        else System.out.println("没有自动化可测试的数据");
        driver.switchTo().defaultContent();
        Thread.sleep(3000);
    }

    //直播发布
    public static void publish() throws InterruptedException {
        recordList(driver);
        Boolean hasPublish=false;

        for (int i = 0; i < 50; i++) {
            if (CommonMethod.isJudgingElement(driver, By.id("layui-layer-iframe" + i))) {
                driver.switchTo().frame("layui-layer-iframe" + i);
                break;
            }
        }
        Thread.sleep(1000);
        List<WebElement> lives = driver.findElements(By.xpath("//div[@class='show-content-data']/div"));
        if (lives.size() > 0){
            for (int i = 0; i < lives.size(); i++) {
                if (lives.get(i).findElement(By.xpath("div[@class='btns']/span[2]")).getAttribute("class").contains("hidden")) {
                    lives.get(i).findElement(By.xpath("div[@class='btns']/span[@class='btn btn-publish']")).click();
                    driver.findElement(By.className("layui-layer-btn0")).click();
                    Thread.sleep(500);
                    driver.findElement(By.className("lz-opt-text")).click();
                    hasPublish=true;
                    System.out.println("~~~ publish()，发布直播，执行成功 ~~~");
                    break;
                }
            }
            if (!hasPublish)
                System.out.println("没有待发布的自动化测试数据");
        }
        else System.out.println("没有自动化可测试的数据");
        driver.findElement(By.className("lz-opt-text")).click();
        Thread.sleep(1000);
        driver.switchTo().defaultContent();
        Thread.sleep(3000);
    }

    //直播签发到测试频道
    public static void push() throws InterruptedException {
        recordList(driver);
        Boolean getTestChannel = false;
        String acticleName = "";

        for (int i = 0; i < 50; i++) {
            if (CommonMethod.isJudgingElement(driver, By.id("layui-layer-iframe" + i))) {
                driver.switchTo().frame("layui-layer-iframe" + i);
                break;
            }
        }
        Thread.sleep(1000);
        List<WebElement> lives = driver.findElements(By.xpath("//div[@class='show-content-data']/div"));
        if (lives.size() > 0) {
            for (int i = 0; i < lives.size(); i++) {
                if (lives.get(i).findElement(By.xpath("div[@class='btns']/span[1]")).getAttribute("class").contains("hidden")) {
                    acticleName = lives.get(i).findElement(By.xpath("div[@class='info']/p[@class='name']/span[@class='title']")).getText();
                    lives.get(i).findElement(By.xpath("div[@class='btns']/span[@class='btn btn-qianfa']")).click();
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("input.layui-input.myKeyword3")).sendKeys("auto");//自动化要签发的频道搜索关键词
                    driver.findElement(By.cssSelector("button.layui-btn.layui-btn-primary.search3")).click();//点击搜索
                    Thread.sleep(500);
                    getTestChannel = CommonMethod.getTestChannel(driver);
                    if (getTestChannel) break;//已选中签发频道则跳出
                }
            }
            if (getTestChannel) {//如果签发成功了
                driver.findElement(By.cssSelector("button.ll-btn.btn-yes.fl")).click();//点击确定签发
                driver.findElement(By.className("lz-opt-text")).click();
                System.out.println("~~~ push()，直播签发，执行成功。直播“" + acticleName + "”签发成功 ~~~");
            } else System.out.println("没有找到auto签发频道");
        }
        Thread.sleep(3000);
    }

    //搜索
    public static void seachAuto() throws InterruptedException {
        driver.findElement(By.id("show-keyword")).clear();
        driver.findElement(By.id("show-keyword")).sendKeys("自动化");
        driver.findElement(By.cssSelector("button.layui-btn.btn-search")).click();
        Thread.sleep(2000);
    }

    //搜索后跳转到回看列表
    public static void recordList(WebDriver driver) throws InterruptedException {
        seachAuto();
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@id='showData']/div"))) {
            driver.findElement(By.xpath("//div[@id='showData']/div[1]/div[@class='btns']/span[@class='btn fr btn-record']")).click();
        } else System.out.println("没有自动化可测试的数据");
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.tagName("header"))) {//校验是否跳转成功
                    driver.get("http://app.test.pdmiryun.com/mp/live/manage");//打开媒体号共享页面
                    Thread.sleep(2000);
                } else break;
            }

            if (!driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a")).getText().contains("爱富县")) {
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a"))).perform();//悬浮到站点名，打开站点图层
                Thread.sleep(500);
                driver.findElement(By.linkText("爱富县")).click();//选择切换到爱富县
                Thread.sleep(2000);
                driver.get("http://app.test.pdmiryun.com/mp/live/manage");//再次切换到媒体号共享
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
