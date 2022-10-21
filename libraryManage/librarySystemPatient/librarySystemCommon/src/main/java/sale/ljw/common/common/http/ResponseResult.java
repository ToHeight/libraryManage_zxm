package sale.ljw.common.common.http;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseResult<T> {
    private T result;
    private String statusCode;
    private String message;
    private Map<String, String> msgParams;
    private ResultType resultType;

    public ResponseResult() {
    }


    public ResponseResult(T result, String message, ResultType rt, String statusCode, Map<String, String> msgParams) {
        super();
        this.result = result;
        this.message = message;
        this.resultType = rt;
        this.statusCode = statusCode;
        this.msgParams = msgParams;
    }

    public static <T> ResponseResult<T> getSuccessResult(T res, String message) {
        return new ResponseResult<T>(res, message, ResultType.SUCCESS, StatusCode.SUCCESS, null);
    }

    public static <T> ResponseResult<T> getSuccessResult(T res, String message, Map<String, String> msgParams) {
        return new ResponseResult<T>(res, message, ResultType.SUCCESS, StatusCode.SUCCESS, msgParams);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String message, String statusCode) {
        return new ResponseResult<T>(res, message, ResultType.MESSAGE, statusCode, null);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String message) {
        return new ResponseResult<T>(res, message, ResultType.MESSAGE, StatusCode.SUCCESS, null);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String message, Map<String, String> msgParams) {
        return new ResponseResult<T>(res, message, ResultType.MESSAGE, StatusCode.SUCCESS, msgParams);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String message, Map<String, String> msgParams, String statusCode) {
        return new ResponseResult<T>(res, message, ResultType.MESSAGE, statusCode, msgParams);
    }

    public static <T> ResponseResult<T> getErrorResult(String message, String status,T res) {
        return new ResponseResult<T>(res, message, ResultType.ERROR, status, null);
    }

    public static <T> ResponseResult<T> getNotFoundResult() {
        return new ResponseResult<T>(null, null, ResultType.NOT_FOUND, StatusCode.NOT_FOUND, null);
    }
}
