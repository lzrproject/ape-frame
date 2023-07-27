package com.paopao.mode.chainPattern;

import com.paopao.mode.chainPattern.easy.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: paoPao
 * @Date: 2023/7/26
 * @Description:
 */
@SpringBootTest
public class EasyTest {

    @Test
    public void test() {
        invoke();
    }

    public void invoke() {
        Approver classAdviser = new ClassAdviser("张老师");
        Approver gradeLeader = new GradeLeader("李主任");
        Approver schoolMaster = new Master("王校长");

        classAdviser.setNext(gradeLeader);
        gradeLeader.setNext(schoolMaster);

        LeaveRequest leaveRequest = new LeaveRequest("小学生", 10);
        classAdviser.approve(leaveRequest);
    }
}
