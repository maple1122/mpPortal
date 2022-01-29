import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/1/29 11:09
 */
public class PaipaiManageTest {
    @Test(priority = 1)//拍拍下线
    public void testUpPublish() throws InterruptedException {
        PaipaiManage.uppublish();
    }

    @Test(priority = 2)//拍拍发布
    public void testPublish() throws InterruptedException {
        PaipaiManage.publish();
    }

//    @Test(priority = 3)//拍拍审核，可审核的数据不多，顾不做审核
//    public void testAuditing() throws InterruptedException {
//        auditing();
//    }

    @Test(priority = 4)//拍拍签发
    public void testPush() throws InterruptedException {
        PaipaiManage.push();
    }

//    @Test(priority = 10)//拍拍删除，可供删除的数据不多，顾不做执行
//    public void testDel() throws InterruptedException {
//        del();
//    }

    @Test(priority = 5)//拍拍解除违规
    public void testRelieve() throws InterruptedException {
        PaipaiManage.relieve();
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