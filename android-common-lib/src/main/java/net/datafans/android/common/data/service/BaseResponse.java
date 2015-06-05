package net.datafans.android.common.data.service;

public class BaseResponse {

    private String data;
    private int status;
    private int errorCode;
    private String errorMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse [data=" + data + ", status=" + status
                + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }


}
