package com.ayu.growing.interceptor;

import com.ayu.growing.config.LoginUserInfo;
import com.ayu.growing.config.LoginUserThreadLocal;
import com.ayu.growing.exception.RequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static String COOKIES_AJSESSIONID_KEY = "_AJSESSIONID";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            //登录校验
//            String sessionId = getCookie(request, COOKIES_AJSESSIONID_KEY);
//            String userAccount = request.getParameter("account");
//            if (StringUtils.isBlank(userAccount)) {
//                return false;
//            }

            LoginUserInfo loginInfo = LoginUserInfo.builder()
                    .userAccount("351")
                    ._AJSESSIONID("342");

            LoginUserThreadLocal.getLocalInfo().set(loginInfo);

        } catch (RequestException e) {
//            BiliLog.error("check auth failed. e = {}", e.getCode());
//            RespUtils.outputCodeMsg(response, e.getCode(), e.getMessage(), e.getShowMsg());
            return false;
        } catch (Exception e) {
//            RespUtils.outputCodeMsg(response, ExceptionEnum.INTERNAL_ERROR.getCode(), ExceptionEnum.INTERNAL_ERROR.getMsg(), ExceptionEnum.COMMON_SHOW_ERROR_MSG);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }
}
