package com.ayu.growing.config;

public class LoginUserThreadLocal {
    public static String getUserAccount() {
        return localInfo.get().getUserAccount();
    }

    public static String getAjsessionid() {
        return localInfo.get().get_AJSESSIONID();
    }

    private static ThreadLocal<LoginUserInfo> localInfo = new ThreadLocal<>();

    public static ThreadLocal<LoginUserInfo> getLocalInfo() {
        return localInfo;
    }

    //长连接，在不需要登录用户信息后，可以手动释放资源，防止内存泄漏
    public static void disPoise() {
        localInfo.remove();
    }
}
