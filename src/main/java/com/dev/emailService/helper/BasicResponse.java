package com.dev.emailService.helper;

import java.util.LinkedHashMap;
import java.util.Map;

public class BasicResponse {

    Integer code;
    String message;
    Object data;

    public BasicResponse(Integer code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BasicResponse(String message, Object data) {
        super();
        this.message = message;
        this.data = data;
    }

    public BasicResponse(Object data) {
        super();
        this.data = data;
    }

    public BasicResponse() {
    }

    public Map<String, Object> success() {
        Map<String, Object> response = new LinkedHashMap<>();

        if (code == null) {
            code = 200;
        }
        response.put("code", code);

        if (message == null) {
            message = "success";
        }
        response.put("success", message);

        response.put("data", data);

        return response;
    }

    public Map<String, Object> error() {
        Map<String, Object> response = new LinkedHashMap<String, Object>();

        if (code == null) {
            code = 801;
        }
        response.put("code", code);

        if (message == null) {
            message = "failed";
        }
        response.put("message", message);

        response.put("data", data);

        return response;
    }

}
