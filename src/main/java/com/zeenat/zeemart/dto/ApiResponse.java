package com.zeenat.zeemart.dto;

public class ApiResponse<T> {
    public boolean success;
    public T data;
    public ErrorInfo error;

    public static class ErrorInfo {
        public String code;
        public String message;
        public ErrorInfo(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = true;
        r.data = data;
        return r;
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = false;
        r.error = new ErrorInfo(code, message);
        return r;
    }
}
