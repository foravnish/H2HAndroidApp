package com.h2h.medical.Volley;

public class CommonJsonResponse {

    private int		status;
    private String message;
    private String option;
    private String serverUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == 1 ? true : false;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

}