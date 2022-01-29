import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/1/29 11:08
 */
public class LiveManageTest {
    @Test(priority = 1)//开启或停用达人直播
    public void testOpen0rClose() throws InterruptedException {
        LiveManage.openOrClose();
    }

    @Test(priority = 4)//下线
    public void testUnpublish() throws InterruptedException {
        LiveManage.unpublish();
    }

    @Test(priority = 2)//发布
    public void testPublish() throws InterruptedException {
        LiveManage.publish();
    }

    @Test(priority = 3)//签发
    public void testPush() throws InterruptedException {
        LiveManage.push();
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