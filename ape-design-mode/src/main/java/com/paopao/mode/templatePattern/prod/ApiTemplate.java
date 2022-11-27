package com.paopao.mode.templatePattern.prod;

import com.paopao.response.Result;
import com.paopao.response.ResultCode;

/**
 * @Author: paoPao
 * @Date: 2022/11/27
 * @Description: api模板
 */
public class ApiTemplate {

    public void execute(final Action action) {
        try {
            action.validate();
            action.execute();
            action.after();

            System.out.println(Result.success(true, ResultCode.SUCCESS, "执行成功"));
        } catch (Exception e){
            System.out.println(Result.success(false, ResultCode.ERROR, "执行失败"));
        }

    }
}
