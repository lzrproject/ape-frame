package com.paopao.excel.constant;

/**
 * 自定义导出异常
 *
 * @Author paopao
 * @Date 2023/07/25
 */
public class ExcelExportException extends RuntimeException {

    private static final long serialVersionUID = 2262689623873971839L;

    public ExcelExportException(String message) {
        super(message);
    }

    public ExcelExportException(Throwable cause) {
        super(cause);
    }

    public ExcelExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
