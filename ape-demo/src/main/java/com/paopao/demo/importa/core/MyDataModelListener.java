//package com.paopao.demo.importa.core;
//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.read.listener.ReadListener;
//import com.paopao.demo.domain.MyDataModel;
//import com.paopao.demo.importa.service.MyDataService;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 描述
// *
// * @Author paoPao
// * @Date 2023/12/31
// */
//public class MyDataModelListener implements ReadListener<MyDataModel> {
//
//    private MyDataService myDataService;
//
//    // 设置批量处理的数据大小
//    private static final int BATCH_SIZE = 1000;
//    // 用于暂存读取的数据，直到达到批量大小
//    private List<MyDataModel> batch = new ArrayList<>();
//
//    // 构造函数，注入MyBatis的Mapper
//    public MyDataModelListener(MyDataService myDataService) {
//        this.myDataService = myDataService;
//    }
//
//    @Override
//    public void invoke(MyDataModel myDataModel, AnalysisContext analysisContext) {
//        //检查数据的合法性及有效性
//        if (validateData(myDataModel)) {
//            //有效数据添加到list中
//            batch.add(myDataModel);
//        } else {
//            // 处理无效数据，例如记录日志或跳过
//        }
//
//        // 当达到批量大小时，处理这批数据
//        if (batch.size() >= BATCH_SIZE) {
//            processBatch();
//        }
//    }
//
//    private boolean validateData(MyDataModel data) {
//        // 调用mapper方法来检查数据库中是否已存在该数据
//        int count = myDataService.countByColumn1(data.getColumn1());
//        // 如果count为0，表示数据不存在，返回true；否则返回false
//        if(count == 0){
//            return true;
//        }
//
//        // 在这里实现数据验证逻辑
//        return false;
//    }
//
//    // 所有数据读取完成后调用此方法
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        // 如果还有未处理的数据，进行处理
//        if (!batch.isEmpty()) {
//            processBatch();
//        }
//    }
//
//    // 处理一批数据的方法
//    private void processBatch() {
//        int retryCount = 0;
//        // 重试逻辑
//        while (retryCount < 3) {
//            try {
//                // 尝试批量插入
//                myDataService.batchInsert(batch);
//                // 清空批量数据，以便下一次批量处理
//                batch.clear();
//                break;
//            } catch (Exception e) {
//                // 重试计数增加
//                retryCount++;
//                // 如果重试3次都失败，记录错误日志
//                if (retryCount >= 3) {
//                    logError(e, batch);
//                }
//            }
//        }
//    }
//
//    // 记录错误日志的方法
//    private void logError(Exception e, List<MyDataModel> failedBatch) {
//        // 在这里实现错误日志记录逻辑
//        // 可以记录异常信息和导致失败的数据
//    }
//}
