package com.bilibili.growing.config;

public class LoginUserInfo {
    private   String   userAccount;

    private  String _AJSESSIONID;

    public String getUserAccount() {
        return userAccount;
    }

    public String get_AJSESSIONID() {
        return _AJSESSIONID;
    }
    private LoginUserInfo(){}

    public static LoginUserInfo builder()
    {
        return new LoginUserInfo();
    }
    public LoginUserInfo userAccount(String userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public LoginUserInfo _AJSESSIONID(String _AJSESSIONID) {
        this._AJSESSIONID = _AJSESSIONID;
        return this;
    }
}
