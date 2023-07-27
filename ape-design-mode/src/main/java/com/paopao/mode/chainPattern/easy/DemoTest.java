package com.paopao.mode.chainPattern.easy;

/**
 * @Author: paoPao
 * @Date: 2023/7/3
 * @Description: 责任链模式
 */
public class DemoTest {

    public static void main(String[] args) {
        invoke();
    }

    public static void invoke() {
        Approver classAdviser = new ClassAdviser("张老师");
        Approver gradeLeader = new GradeLeader("李主任");
        Approver schoolMaster = new Master("王校长");

        classAdviser.setNext(gradeLeader);
        gradeLeader.setNext(schoolMaster);

        LeaveRequest leaveRequest = new LeaveRequest("小学生", 10);
        classAdviser.approve(leaveRequest);
    }
}
