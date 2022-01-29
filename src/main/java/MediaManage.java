import base.CommonMethod;
import base.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * @author wufeng
 * @date 2022/1/28 17:30
 */
public class MediaManage extends Login {

    static WebDriver driver;

    //新建分类
    public static String addClass(String className) throws InterruptedException {
        driver.findElement(By.className("add-group-btn")).click();//点击添加分类
        Thread.sleep(500);
        driver.findElement(By.xpath("//form[@class='addGroup-form layui-form']/div[2]/div/input")).sendKeys(className);//分类名称
        driver.findElement(By.xpath("//form[@class='addGroup-form layui-form']/div[3]/div/input")).sendKeys("auto" + System.currentTimeMillis());//分类编码
        driver.findElement(By.xpath("//form[@class='addGroup-form layui-form']/div[4]/div/div[2]")).click();//设置为不做展示
        driver.findElement(By.xpath("//form[@class='addGroup-form layui-form']/div[5]/div/div")).click();//设置分类logo
        Thread.sleep(500);
        CommonMethod.getImg(driver);//在线资源库设置封面图
        Thread.sleep(500);
        driver.findElement(By.cssSelector("button.ll-btn.btn-yes.fl")).click();//点击保存
        Thread.sleep(3000);
        System.out.println("~~~ addClass()，添加分类，执行成功 ~~~");
        return driver.findElement(By.xpath("//ul[@class='media-group']/li[last()]/div/span")).getText();//返回保存后的名称
    }

    //删除分类
    public static void delClass() throws InterruptedException {
        Boolean canDel = false;
        List<WebElement> classes = driver.findElements(By.xpath("//ul[@class='media-group']/li"));//获取分类list
        if (classes.size() > 0)
            for (int i = 0; i < classes.size(); i++) {
                if (classes.get(i).findElement(By.xpath("div/span")).getText().contains("auto")) {//获取名称包含auto的分类
                    classes.get(i).click();//激活auto的分类
                    Thread.sleep(1000);
                    if (!CommonMethod.isJudgingElement(driver,By.xpath("//div[@id='mediaList']/a"))) {//取无媒体号的分类，有媒体号的不可删除
                        classes.get(i).findElement(By.xpath("div/div/span[2]")).click();//点击删除
                        Thread.sleep(500);
                        driver.findElement(By.className("layui-layer-btn0")).click();//确认删除
                        canDel = true;
                        System.out.println("~~~ delClass()，删除分类，执行成功 ~~~");
                        break;
                    }
                }
            }
        if (!canDel) System.out.println("没有可删除的自动化分类");
        Thread.sleep(2000);
    }

    //编辑分类
    public static String editClass(String className) throws InterruptedException {
        String classname1 = null;
        List<WebElement> classes = driver.findElements(By.xpath("//ul[@class='media-group']/li"));//获取分类list
        if (classes.size() > 0)
            for (int i = 0; i < classes.size(); i++) {
                if (classes.get(i).findElement(By.xpath("div/span")).getText().contains("auto")) {//获取名称包含auto的分类
                    classes.get(i).click();//激活auto分类
                    Thread.sleep(500);
                    classes.get(i).findElement(By.xpath("div/div/span[1]")).click();//点击编辑
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//form[@class='addGroup-form layui-form']/div[2]/div/input")).clear();//清空input编辑框
                    driver.findElement(By.xpath("//form[@class='addGroup-form layui-form']/div[2]/div/input")).sendKeys(className);//录入新的名称
                    driver.findElement(By.cssSelector("button.ll-btn.btn-yes.fl")).click();//保存
                    Thread.sleep(2000);
                    classname1 = driver.findElement(By.xpath("//ul[@class='media-group']/li[" + (i + 1) + "]/div/span")).getText();//返回保存后的名称
                    System.out.println("~~~ editClass()，编辑分类，执行成功 ~~~");
                    break;
                }
            }
        Thread.sleep(2000);
        return classname1;
    }

    //添加媒体号
    public static void addMedia() throws InterruptedException {
        String classname;
        List<WebElement> classes = driver.findElements(By.xpath("//ul[@class='media-group']/li"));//获取分类列表
        List<WebElement> classes2;//设置媒体号分类的list
        if (classes.size() > 0) {
            for (int i = 0; i < classes.size(); i++) {
                classname = classes.get(i).findElement(By.xpath("div/span")).getText();//获取分类名称
                if (classname.contains("auto")) {//取分类名称中包含auto的分类
                    classes.get(i).findElement(By.xpath("div/span")).click();//激活auto分类
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("a.add-media-btn.hidden")).click();//点击添加媒体号
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//form[@class='layui-form basicinfo-form']/div/div/input")).sendKeys("autoTest" + Calendar.getInstance().getTimeInMillis());//媒体号名称
                    driver.findElement(By.xpath("//form[@class='layui-form basicinfo-form']/div[2]/div/input")).sendKeys("autoTest" + Calendar.getInstance().getTimeInMillis());//媒体号编码
                    driver.findElement(By.xpath("//form[@class='layui-form basicinfo-form']/div[3]/div/div")).click();//设置为个人类型
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//form[@class='layui-form basicinfo-form']/div[5]/div/input")).sendKeys("autoTest");//真实姓名
                    driver.findElement(By.xpath("//form[@class='layui-form basicinfo-form']/div[6]/div/input")).sendKeys("111111111111111111");//身份证号
                    Random r = new Random();//创建随机数对象
                    driver.findElement(By.xpath("//form[@class='layui-form basicinfo-form']/div[7]/div/input")).sendKeys("189" + (r.nextInt(89999999) + 10000000));//设置手机号，189+8位随机数
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//div[@class='layui-form-item personClasses']/div/input")).click();//打开分类设置列表
                    Thread.sleep(500);

                    classes2 = driver.findElements(By.xpath("//ul[@class='groupselecct']/div"));//获取分类list
                    if (classes2.size() > 0)
                        for (int j = 0; j < classes2.size(); j++) {
                            if (classes2.get(j).findElement(By.xpath("div/span")).getText().contains("autoTest")) {//判断分类名称是否有autoTest
                                classes2.get(j).findElement(By.xpath("div/i")).click();//取autoTest分类
                                Thread.sleep(500);
                                break;
                            }
                        }
                    driver.switchTo().frame("LAY_layedit_1");//切换到简介iframe页面
                    Thread.sleep(200);
                    driver.findElement(By.tagName("body")).click();//激活简介图层可编辑状态
                    Thread.sleep(100);
                    driver.findElement(By.tagName("body")).sendKeys("这里是简介");//录入简介内容
                    driver.switchTo().defaultContent();//返回到默认页面
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//form[@class='layui-form basicinfo-form']/div[21]/div/div[2]/i")).click();//设置为默认不启用
                    Thread.sleep(500);
                    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");//滚动条滚动到最上方
                    Thread.sleep(1000);
                    driver.findElement(By.id("saveMedia")).click();//点击保存
                    System.out.println("~~~ addMedia()，添加媒体号，执行成功 ~~~");
                    Thread.sleep(2000);
                    break;
                }
            }
        }
    }

    //删除媒体号
    public static void delMedia() throws InterruptedException {
        if (getAuto()) {//定位到自动化auto分类
            String mediaName;
            Boolean del=false;
            if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@id='mediaList']/a"))) {//判断是否有媒体号
                List<WebElement> medias = driver.findElements(By.xpath("//div[@id='mediaList']/a"));//获取媒体号列表
                for (int i = 0; i < medias.size(); i++) {
                    medias.get(i).click();
                    Thread.sleep(2000);
                    if (!driver.findElement(By.id("delMedia")).getAttribute("class").contains("hidden")) {
                        mediaName = driver.findElement(By.xpath("//div[@id='mediaList']/a["+(i+1)+"]")).getText();//获取媒体号名称
                        driver.findElement(By.id("delMedia")).click();//点击删除
                        driver.findElement(By.className("layui-layer-btn0")).click();//确定删除
                        System.out.println("~~~ delMedia()，删除媒体号，执行成功。" + mediaName + "已被删除 ~~~");
                        del = true;
                        Thread.sleep(1000);
                        break;
                    }
                }
                if (!del) System.out.println("无媒体号可删除");
            } else System.out.println("无媒体号可删除");
        } else System.out.println("没有自动化分类");
        Thread.sleep(2000);
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver,By.tagName("header"))) {//校验是否跳转成功
                    driver.get("http://app.test.pdmiryun.com/mp/media/manage");
                    Thread.sleep(2000);
                } else break;
            }

            if (!driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a")).getText().contains("爱富县")) {//校验是否是爱富县
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a"))).perform();//悬浮打开机构list
                Thread.sleep(500);
                driver.findElement(By.linkText("爱富县")).click();//切换到爱富县
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //定位到auto分类
    public static Boolean getAuto() throws InterruptedException {
        String classname;
        List<WebElement> classes = driver.findElements(By.xpath("//ul[@class='media-group']/li"));//获取分类列表
        if (classes.size() > 0) {
            for (int i = 0; i < classes.size(); i++) {
                classname = classes.get(i).findElement(By.xpath("div/span")).getText();//获取分类名称
                if (classname.contains("auto")) {//名称是否包含auto
                    classes.get(i).findElement(By.xpath("div/span")).click();//激活auto分类
                    Thread.sleep(1000);
                    break;
                }
            }
            return true;
        } else return false;
    }

}
