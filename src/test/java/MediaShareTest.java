import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * @author wufeng
 * @date 2022/1/29 11:09
 */
public class MediaShareTest {
    @Test(priority = 1)//我要共享-媒体号设置或取消共享
    public void testSetting() throws InterruptedException {
        MediaShare.setting();
    }

    @Test(priority = 2)//共享媒体号-引入共享媒体号
    public void testIntroduce() throws InterruptedException {
        MediaShare.introduce();
    }

    @Test(priority = 3)//引入媒体号-删除引入媒体号
    public void testDelIntroduce() throws InterruptedException {
        MediaShare.delIntroduce();
    }

    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Test case: "
                + method.getName());
    }

    @AfterMethod
    public void testEnd(Method method){
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<< Test End!\n");
    }
}