package sale.ljw.common.common.http;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseResult<T> {
    private T result;
    private String statusCode;
    private String msgId;
    private Map<String, String> msgParams;
    private ResultType resultType;

    public ResponseResult() {
    }


    public ResponseResult(T result, String msgId, ResultType rt, String statusCode, Map<String, String> msgParams) {
        super();
        this.result = result;
        this.msgId = msgId;
        this.resultType = rt;
        this.statusCode = statusCode;
        this.msgParams = msgParams;
    }

    public static <T> ResponseResult<T> getSuccessResult(T res, String msgId) {
        return new ResponseResult<T>(res, msgId, ResultType.SUCCESS, StatusCode.SUCCESS, null);
    }

    public static <T> ResponseResult<T> getSuccessResult(T res, String msgId, Map<String, String> msgParams) {
        return new ResponseResult<T>(res, msgId, ResultType.SUCCESS, StatusCode.SUCCESS, msgParams);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String msgId, String statusCode) {
        return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, statusCode, null);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String msgId) {
        return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, StatusCode.SUCCESS, null);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String msgId, Map<String, String> msgParams) {
        return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, StatusCode.SUCCESS, msgParams);
    }

    public static <T> ResponseResult<T> getMessageResult(T res, String msgId, Map<String, String> msgParams, String statusCode) {
        return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, statusCode, msgParams);
    }

    public static <T> ResponseResult<T> getErrorResult(String msgId, String status,T res) {
        return new ResponseResult<T>(res, msgId, ResultType.ERROR, status, null);
    }

    public static <T> ResponseResult<T> getNotFoundResult() {
        return new ResponseResult<T>(null, null, ResultType.NOT_FOUND, StatusCode.NOT_FOUND, null);
    }
}
