package com.paopao.demo.importa.core;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.paopao.demo.domain.MyDataModel;
import com.paopao.demo.domain.vo.JhemrCda02VO;
import com.paopao.demo.service.JhemrCda02Service;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2023/12/31
 */
@Slf4j
public class MyDataModelListener implements ReadListener<JhemrCda02VO> {

    private JhemrCda02Service jhemrCda02Service;

    // 设置批量处理的数据大小
    private static final int BATCH_SIZE = 1000;
    // 用于暂存读取的数据，直到达到批量大小
    private ThreadLocal<List<JhemrCda02VO>> batch = ThreadLocal.withInitial(ArrayList::new);

    // 构造函数，注入MyBatis的Mapper
    public MyDataModelListener(JhemrCda02Service jhemrCda02Service) {
        this.jhemrCda02Service = jhemrCda02Service;
    }

    @Override
    public void invoke(JhemrCda02VO jhemrCda02VO, AnalysisContext analysisContext) {
        List<JhemrCda02VO> jhemrCda02VOS = batch.get();
        //检查数据的合法性及有效性
        if (validateData(jhemrCda02VO)) {
            //有效数据添加到list中
            jhemrCda02VOS.add(jhemrCda02VO);
        } else {
            // 处理无效数据，例如记录日志或跳过
        }

        // 当达到批量大小时，处理这批数据
        if (jhemrCda02VOS.size() >= BATCH_SIZE) {
            processBatch();
        }
    }

    private boolean validateData(JhemrCda02VO jhemrCda02VO) {
//        // 调用mapper方法来检查数据库中是否已存在该数据
//        int count = myDataService.countByColumn1(data.getColumn1());
//        // 如果count为0，表示数据不存在，返回true；否则返回false
//        if(count == 0){
        return true;
//        }

        // 在这里实现数据验证逻辑
//        return false;
    }

    // 所有数据读取完成后调用此方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        List<JhemrCda02VO> jhemrCda02VOS = batch.get();
        // 如果还有未处理的数据，进行处理
        if (!jhemrCda02VOS.isEmpty()) {
            log.info("MyDataModelListener.doAfterAllAnalysed size:{}", jhemrCda02VOS.size());
            processBatch();
        }
    }

    // 处理一批数据的方法
    private void processBatch() {
        List<JhemrCda02VO> jhemrCda02VOS = batch.get();
        int retryCount = 0;
//        // 重试逻辑
        while (retryCount < 3) {
            try {
                // 尝试批量插入
                jhemrCda02Service.batchInsertCda02(jhemrCda02VOS);
                // 清空批量数据，以便下一次批量处理
                jhemrCda02VOS.clear();
                break;
            } catch (Exception e) {
                // 重试计数增加
                retryCount++;
                // 如果重试3次都失败，记录错误日志
                if (retryCount >= 3) {
                    logError(e, jhemrCda02VOS);
                }
            }
        }
    }

    // 记录错误日志的方法
    private void logError(Exception e, List<JhemrCda02VO> jhemrCda02VOS) {
        // 在这里实现错误日志记录逻辑
        log.error("import.logError 执行失败", e);
        // 可以记录异常信息和导致失败的数据
    }
}
