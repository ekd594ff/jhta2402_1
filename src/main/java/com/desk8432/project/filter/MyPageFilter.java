package com.desk8432.project.filter;

import com.desk8432.project.dto.member.LoginMemberDTO;
import com.desk8432.project.util.CookieManager;
import com.desk8432.project.util.ScriptWriter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({"/mypage/*", "/schedule/*", "/group/*"})
public class MyPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        Cookie usernameCookie = readCookie(httpRequest, "username");
        LoginMemberDTO loginMemberDTO = (LoginMemberDTO) session.getAttribute("member");

        if (usernameCookie != null && loginMemberDTO != null
                && loginMemberDTO.getUsername().equals(usernameCookie.getValue())) {
            chain.doFilter(request, response);
        } else {
            CookieManager.deleteCookie(httpResponse, "username");
            session.setAttribute("member", null);
            ScriptWriter.alertAndNext(httpResponse, "로그인이 필요합니다.", "/signin");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }

    private Cookie readCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
