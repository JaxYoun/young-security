package com.young.youngsecurity.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/20
 */
public interface WebUtil {

    static void renderString(HttpServletResponse response, String string) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(string);
    }

}
