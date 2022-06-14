import base.CommonMethod;
import base.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/1/29 11:06
 */
public class MediaShare extends Login {
    static WebDriver driver;

    //我要共享-共享设置，媒体号设置或取消共享
    public static void setting() throws InterruptedException {

        Boolean status = false;

        searchAuto("auto");//搜索auto名称的媒体号
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"))) {//判断列表是否有数据
            if (!driver.findElement(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr[1]/td[@data-field='mediaName']/div/p")).getText().contains("auto")) {//列表数据是否是auto媒体号
                MediaManage.addMedia();//没auto媒体号则先添加auto媒体号
                Thread.sleep(2000);
                driver.get("http://app.test.pdmiryun.com/mp/mpshare/mediaShare");//添加成功后返回到媒体号共享页面
                Thread.sleep(1000);
                searchAuto("auto");//再次搜索auto名称的媒体号
                Thread.sleep(1000);
            }
        }
        List<WebElement> medias = driver.findElements(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"));//获取媒体号数据列表
        String mediaName = medias.get(0).findElement(By.xpath("td[3]/div/p")).getText();//取第一个媒体号名称
        medias.get(0).findElement(By.xpath("td[5]/div/span")).click();//点击第一个媒体号的共享设置，打开共享设置图层
        Thread.sleep(1000);
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='checkbox-groups']/li"))) {//判断共享设置图层是否有站点数据
            driver.findElement(By.xpath("//div[@class='lft-list']/p/div/i")).click();//点击全选，已设置过的则取消共享，未设置过的则设置共享
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='hxj-setting']/div[@class='btn-group']/button[1]")).click();//点击确定保存
            Thread.sleep(2000);
            if (driver.findElement(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr/td[4]/div/p")).getText().isEmpty()) {//校验保存后该媒体号是否有拟共享客户端
                System.out.println("~~~ setting()，媒体号共享设置，执行成功。媒体号：" + mediaName + " 被取消共享 ~~~");
            } else {
                status = true;
                System.out.println("~~~ setting()，媒体号共享设置，执行成功。媒体号：" + mediaName + " 被设置共享 ~~~");
            }
        } else System.out.println("没有站点数据");
    }

    //共享媒体号-引入
    public static void introduce() throws InterruptedException {

        Boolean status = false;
        driver.findElement(By.xpath("//div[@class='type-nav clearfix']/p[2]")).click();//切换到共享媒体号页面
        Thread.sleep(1000);

        searchAuto("wf");//搜索名称有“wf”的媒体号
        Thread.sleep(1000);

        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"))) {//判断是否有名称包含wf的被共享的媒体号数据
            List<WebElement> medias = driver.findElements(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"));//获取名包含wf的被共享的媒体号列表
            String mediaName = medias.get(0).findElement(By.xpath("td[@data-field='mediaName']/div/p")).getText();//获取名称包含有wf的媒体号名称
            medias.get(0).findElement(By.xpath("td[@data-field='7']/div/span")).click();//点击该媒体号的“引入”按钮，打开引入图层
            Thread.sleep(500);
            List<WebElement> mediaClasses = driver.findElements(By.xpath("//div[@id='hxj-channelTree']/div"));//获取分类列表
            for (int i = 0; i < mediaClasses.size(); i++) {
                if (mediaClasses.get(i).findElement(By.xpath("div/span")).getText().contains("auto")) {//判断是否有auto分类
                    status = mediaClasses.get(i).findElement(By.xpath("div")).getAttribute("class").contains("layui-form-checked");//如果已被勾选则为取消引入；未被勾选则为引入
                    mediaClasses.get(i).findElement(By.xpath("div/i")).click();//复选框操作，勾选或取消勾选
                    Thread.sleep(500);
                    break;
                }
            }
            driver.findElement(By.xpath("//div[@class='hxj-introduce']/div[@class='btn-group']/button[1]")).click();//保存确定
            if (!status)//引入操作
                System.out.println("~~~ introduce()，引入共享媒体号，执行成功。" + mediaName + "被引入到auto分类 ~~~");
            else//取消引入
                System.out.println("~~~ introduce()，引入共享媒体号，执行成功。" + mediaName + "被取消引入到auto分类 ~~~");
            Thread.sleep(3000);
        } else System.out.println("无数据");
    }

    //引入媒体号-删除引入
    public static void delIntroduce() throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='type-nav clearfix']/p[3]")).click();//切换到引入媒体号页面
        Thread.sleep(1000);

        searchAuto("wf");//搜索名称有“wf”的媒体号
        Thread.sleep(1000);

        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"))) {//判断是否有名称包含wf的被引入的媒体号数据
            List<WebElement> medias = driver.findElements(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"));//获取名包含wf的被引入的媒体号列表
            String mediaName = medias.get(0).findElement(By.xpath("td[@data-field='mediaName']/div/p")).getText();//获取名称包含有wf的媒体号名称
            if (!mediaName.contains("wf")) {
                introduce();//进行引入wf的媒体号
                Thread.sleep(2000);
                driver.findElement(By.xpath("//div[@class='type-nav clearfix']/p[3]")).click();//切换到引入媒体号页面
                Thread.sleep(1000);
                searchAuto("wf");//搜索名称有“wf”的媒体号
                Thread.sleep(1000);
                mediaName = driver.findElement(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr/td[@data-field='mediaName']/div/p")).getText();//重新获取包含wf的媒体号名称
            }
            driver.findElement(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr/td[@data-field='7']/div/span")).click();//点击该媒体号的“引入”按钮，打开引入图层
            Thread.sleep(500);
            driver.findElement(By.className("layui-layer-btn0")).click();//确定删除
            System.out.println("~~~ delIntroduce()，删除引入，执行成功。" + mediaName + "被删除引入 ~~~");
        } else System.out.println("无数据");
    }

    //搜索auto媒体号
    public static void searchAuto(String key) throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='mySearchBox fr marglft_15']/input")).sendKeys(key);//录入搜索关键词
        driver.findElement(By.xpath("//div[@class='mySearchBox fr marglft_15']/button")).click();//搜索
        Thread.sleep(1000);
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.tagName("header"))) {//校验是否跳转成功
                    driver.get(domain+"/mp/mpshare/mediaShare");//打开媒体号共享页面
                    Thread.sleep(2000);
                } else break;
            }

            if (!driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a")).getText().contains(siteName)) {
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a"))).perform();//悬浮到站点名，打开站点图层
                Thread.sleep(500);
                driver.findElement(By.linkText(siteName)).click();//选择切换到爱富县
                Thread.sleep(2000);
                driver.get(domain+"/mp/mpshare/mediaShare");//再次切换到媒体号共享
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
