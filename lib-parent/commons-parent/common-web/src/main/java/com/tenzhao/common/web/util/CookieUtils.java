package com.tenzhao.common.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public final class CookieUtils {
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void addCookie(HttpServletResponse response, String domain, String path, String name, String value) {
        addCookie(response, domain, path, name, value, -1);
    }

    /**
     * 域名不能为localhost 以及ip冒号端口,
     * @param response
     * @param domain
     * @param path
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        if(domain.indexOf(":")>=0){
        	domain = domain.split(":")[0] ; //将带冒号的域名分割成正常域名
        }
        cookie.setDomain(domain);
        if(StringUtils.isNotEmpty(path))
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse response, String domain, String path, String name) {
        addCookie(response, domain, path, name, null, 0);
    }

    private CookieUtils() {
    }
}
