import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/1/29 10:57
 */
public class MediaManageTest {
    @Test(priority = 1)//添加媒体号分类
    public void testAddClass() throws InterruptedException {
        Random r = new Random();
        String className = "autoTest" + (r.nextInt(899999) + 100000);
        String className1 = MediaManage.addClass(className);
        Assert.assertEquals(className1, className, "testAddClass() 执行失败");
    }

    @Test(priority = 2)//编辑媒体号分类
    public void testEditClass() throws InterruptedException {
        Random r = new Random();
        String className = "autoTest" + (r.nextInt(899999) + 100000);
        String className1 = MediaManage.editClass(className);
        Assert.assertEquals(className1, className, "testEditClass() 执行失败");
    }

//    @Test(priority = 3)//删除媒体号分类
//    public void testDelClass() throws InterruptedException {
//        MediaManage.delClass();
//    }

    @Test(priority = 4)//添加媒体号
    public void testAddMedia() throws InterruptedException {
        for (int i = 0; i < 22; i++) {
            MediaManage.addMedia();
        }
    }

//    @Test(priority = 5)//删除媒体号
//    public void testDelMedia() throws InterruptedException {
//        MediaManage.delMedia();
//    }

    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Test case: "
                + method.getName());
    }

    @AfterMethod
    public void testEnd(Method method) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<< Test End!\n");
    }
}