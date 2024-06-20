package org.desk8432.project.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieManager {
    public static void createCookie(HttpServletResponse response,
                                    String cookieName,
                                    String cookieValue,
                                    int second) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(second);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String readCookie(HttpServletRequest request,String cookieName) {
        Cookie[] cookies = request.getCookies();
        String cookieValue = "";
        if(cookies!=null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(cookieName)) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }
        return cookieValue;
    }

    public static void deleteCookie(HttpServletResponse response,
                                    String cookieName) {
        createCookie(response,cookieName,null,0);
    }
}
