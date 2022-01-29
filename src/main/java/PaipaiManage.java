import base.CommonMethod;
import base.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/1/29 11:07
 */
public class PaipaiManage extends Login {
    static WebDriver driver;

    //拍拍下线
    public static void uppublish() throws InterruptedException {
        seachAuto();

        Actions actions = new Actions(driver);
        Boolean upPublish = false;//是否下线成功了
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='lists clearfix']/li"))) {//是否有搜索后的数据
            List<WebElement> videos = driver.findElements(By.xpath("//ul[@class='lists clearfix']/li"));//获取拍拍视频列表
            for (int i = 0; i < videos.size(); i++) {
                if (videos.get(i).findElement(By.xpath("./div[@class='action']/img[@class='status']")).getAttribute("src").contains("publish")) {//校验是否是已发布的数据
                    actions.moveToElement(videos.get(i)).perform();//鼠标悬浮
                    Thread.sleep(500);
                    videos.get(i).findElement(By.xpath("./div[@class='action']/div[@class='btn-group']/span[@class='btn btn-xiaxian']")).click();//点击下线
                    Thread.sleep(500);
                    driver.findElement(By.className("layui-layer-btn0")).click();//点击确定
                    Thread.sleep(1000);
                    upPublish = true;//标识为已下线
                    System.out.println("~~~ uppublish()，拍拍下线，执行成功 ~~~");
                    break;
                }
            }
            if (!upPublish) System.out.println("没有已审核通过的拍拍可下线");
        } else System.out.println("没有数据");
        Thread.sleep(3000);
    }

    //拍拍发布
    public static void publish() throws InterruptedException {
        seachAuto();

        Actions actions = new Actions(driver);
        Boolean upPublish = false;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='lists clearfix']/li"))) {//校验是否有数据返回
            List<WebElement> videos = driver.findElements(By.xpath("//ul[@class='lists clearfix']/li"));//获取拍拍视频列表
            for (int i = 0; i < videos.size(); i++) {
                if (videos.get(i).findElement(By.xpath("./div[@class='action']/img[@class='status']")).getAttribute("src").contains("line")) {//校验是否是已下线状态
                    actions.moveToElement(videos.get(i)).perform();//光标悬浮
                    Thread.sleep(500);
                    videos.get(i).findElement(By.xpath("./div[@class='action']/div[@class='btn-group']/span[@class='btn btn-fabu']")).click();//点击发布
                    Thread.sleep(500);
                    driver.findElement(By.className("layui-layer-btn0")).click();//确认发布
                    Thread.sleep(1000);
                    upPublish = true;
                    System.out.println("~~~ publish()，拍拍发布，执行成功 ~~~");
                    break;
                }
            }
            if (!upPublish) System.out.println("没有已审下线的拍拍可发布");
        } else System.out.println("没有数据");
        Thread.sleep(3000);
    }

    //拍拍审核
    public static void auditing() throws InterruptedException {
        seachAuto();

        Actions actions = new Actions(driver);
        Boolean upPublish = false;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='lists clearfix']/li"))) {//判断是否有数据
            List<WebElement> videos = driver.findElements(By.xpath("//ul[@class='lists clearfix']/li"));//获取数据list
            for (int i = 0; i < videos.size(); i++) {
                if (videos.get(i).findElement(By.xpath("./div[@class='action']/img[@class='status']")).getAttribute("src").contains("dsh")) {//校验状态是否是待审核
                    actions.moveToElement(videos.get(i)).perform();//光标悬浮
                    Thread.sleep(500);
                    videos.get(i).findElement(By.xpath("./div[@class='action']/div[@class='btn-group']/span[@class='btn btn-shenhe']")).click();//点击审核
                    Thread.sleep(500);
                    driver.findElement(By.className("layui-layer-btn0")).click();//点击确定
                    Thread.sleep(1000);
                    upPublish = true;
                    System.out.println("~~~ auditing()，拍拍审核，执行成功 ~~~");
                    break;
                }
            }
            if (!upPublish) System.out.println("没有待审核的拍拍可审核");
        } else System.out.println("没有数据");
        Thread.sleep(3000);
    }

    //拍拍签发
    public static void push() throws InterruptedException {
        seachAuto();

        Actions actions = new Actions(driver);
        Boolean getTestChannel, push = false;

        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='lists clearfix']/li"))) {//校验是否有数据
            List<WebElement> videos = driver.findElements(By.xpath("//ul[@class='lists clearfix']/li"));//获取数据列表
            for (int i = 0; i < videos.size(); i++) {
                if (videos.get(i).findElement(By.xpath("./div[@class='action']/img[@class='status']")).getAttribute("src").contains("publish")) {//校验是否是已发布状态
                    actions.moveToElement(videos.get(i)).perform();
                    Thread.sleep(500);
                    videos.get(i).findElement(By.xpath("./div[@class='action']/div[@class='btn-group']/span[@class='btn btn-qianfa']")).click();
                    Thread.sleep(1000);
                    driver.findElement(By.cssSelector("input.layui-input.myKeyword3")).sendKeys("auto");//自动化要签发的频道搜索关键词
                    driver.findElement(By.cssSelector("button.layui-btn.layui-btn-primary.search3")).click();//点击搜索
                    Thread.sleep(500);

                    getTestChannel = CommonMethod.getTestChannel(driver);//选择测试频道
                    Thread.sleep(1000);

                    if (getTestChannel) {//如果签发成功了
                        driver.findElement(By.cssSelector("button.ll-btn.btn-yes.fl")).click();//点击确定签发
                        System.out.println("~~~ push()，拍拍签发，执行成功。 ~~~");
                        push = true;
                    } else System.out.println("没有找到auto签发频道");
                    break;
                }
            }
            if (!push) System.out.println("没有已审核通过的拍拍可签发");
        } else System.out.println("没有数据");
        Thread.sleep(3000);
    }

    //拍拍删除
    public static void del() throws InterruptedException {
        seachAuto();

        Actions actions = new Actions(driver);
        Boolean del = false;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='lists clearfix']/li"))) {//校验是否有数据
            List<WebElement> videos = driver.findElements(By.xpath("//ul[@class='lists clearfix']/li"));//获取数据列表
            for (int i = 0; i < videos.size(); i++) {
                if (!videos.get(i).findElement(By.xpath("./div[@class='action']/img[@class='status']")).getAttribute("src").contains("publish")) {//校验是否是已发布状态
                    actions.moveToElement(videos.get(i)).perform();
                    Thread.sleep(500);
                    videos.get(i).findElement(By.xpath("./div[@class='action']/div[@class='btn-group']/span[@class='btn btn-shanchu']")).click();
                    Thread.sleep(1000);
                    driver.findElement(By.className("layui-layer-btn0")).click();
                    System.out.println("~~~ del()，拍拍删除，执行成功 ~~~");
                    del = true;
                    Thread.sleep(1000);
                    break;
                }
            }
            if (!del) System.out.println("没有已下线或待审核状态可删除的拍拍");
        } else System.out.println("没有数据");
    }

    //解除违规
    public static void relieve() throws InterruptedException {
        driver.findElement(By.className("yswg-entrance")).click();//点击打开疑似违规列表
        Thread.sleep(2000);

        for (int i = 1; i < 50; i++) {
            if (CommonMethod.isJudgingElement(driver, By.id("layui-layer-iframe" + i))) {//判断iframe有效名称
                driver.switchTo().frame("layui-layer-iframe" + i);//切换到疑似违规列表iframe
                Thread.sleep(500);

                seachAuto();
                Actions actions = new Actions(driver);
                if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='lists clearfix']/li/div"))) {//校验是否有数据
                    List<WebElement> videos = driver.findElements(By.xpath("//ul[@class='lists clearfix']/li"));//获取数据列表
                    actions.moveToElement(videos.get(0)).perform();//鼠标悬浮
                    Thread.sleep(500);
                    videos.get(0).findElement(By.xpath("./div[@class='action']/div[@class='btn-group']/span[@class='btn btn-jiechu']")).click();//点击解除
                    Thread.sleep(1000);
                    driver.findElement(By.className("layui-layer-btn0")).click();//确认解除
                    Thread.sleep(1000);
                    System.out.println("~~~ relieve()，拍拍解除违规，执行成功 ~~~");
                } else System.out.println("没有可测试的需要解除违规的拍拍");
                driver.findElement(By.className("opt-text")).click();
                Thread.sleep(2000);
                driver.switchTo().defaultContent();//切换到默认页面
                break;
            }
        }
    }

    //搜索
    public static void seachAuto() throws InterruptedException {
        driver.findElement(By.cssSelector("input.layui-input.myphone")).clear();
        driver.findElement(By.cssSelector("input.layui-input.myphone")).sendKeys("18901065191");
        driver.findElement(By.xpath("//div[@class='sousuo fl']/button")).click();
        Thread.sleep(2000);
    }


    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.tagName("header"))) {//校验是否跳转成功
                    driver.get("http://app.test.pdmiryun.com/mp/paipai/manage");//打开媒体号共享页面
                    Thread.sleep(2000);
                } else break;
            }

            if (!driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a")).getText().contains("爱富县")) {
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a"))).perform();//悬浮到站点名，打开站点图层
                Thread.sleep(500);
                driver.findElement(By.linkText("爱富县")).click();//选择切换到爱富县
                Thread.sleep(2000);
                driver.get("http://app.test.pdmiryun.com/mp/paipai/manage");//再次切换到媒体号共享
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
