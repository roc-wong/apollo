package com.ctrip.framework.apollo.portal.spi.zts.portal.domain;

/**
 * timestamp : 1480492468879
 * status : 400
 * error : Bad Request
 * exception : com.zts.common.spec.exception.InvalidArgumentException
 * message : 对照关系冲突
 * path : /v1/users/convert
 *
 * @author roc
 * @since 2020/6/10 13:58
 */
public class ApiErrorResponse {
    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiErrorResponse{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", status=").append(status);
        sb.append(", error='").append(error).append('\'');
        sb.append(", exception='").append(exception).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
