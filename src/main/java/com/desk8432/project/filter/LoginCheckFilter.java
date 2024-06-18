package com.desk8432.project.filter;

import com.desk8432.project.dto.member.LoginMemberDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({"/mypage/*", "/schedule/*"})
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie usernameCookie = readCookie(httpRequest, "username");
        HttpSession session = httpRequest.getSession();
        LoginMemberDTO loginMemberDTO = (LoginMemberDTO) session.getAttribute("member");

        if (usernameCookie != null && loginMemberDTO != null
                && loginMemberDTO.getUsername().equals(usernameCookie.getValue())) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/signin");
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
