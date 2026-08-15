package com.zeenat.zeemart.util;

import com.google.gson.Gson;
import com.zeenat.zeemart.dto.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class JsonUtil {
    private static final Gson GSON = new Gson();
    private JsonUtil() {}

    public static <T> T readBody(HttpServletRequest req, Class<T> clazz) throws IOException {
        return GSON.fromJson(req.getReader(), clazz);
    }

    public static void write(HttpServletResponse resp, int status, ApiResponse<?> body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(GSON.toJson(body));
    }

    public static void ok(HttpServletResponse resp, Object data) throws IOException {
        write(resp, 200, ApiResponse.ok(data));
    }

    public static void created(HttpServletResponse resp, Object data) throws IOException {
        write(resp, 201, ApiResponse.ok(data));
    }

    public static void error(HttpServletResponse resp, int status, String code, String message) throws IOException {
        write(resp, status, ApiResponse.fail(code, message));
    }
}
