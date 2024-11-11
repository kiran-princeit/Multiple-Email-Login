package com.info.multiple.email.onplace.login.model;

public class AccountData {
    private String accountName;
    private int emailType;
    private String loginUrl;

    public AccountData(String accountName, int emailType, String loginUrl) {
        this.accountName = accountName;
        this.emailType = emailType;
        this.loginUrl = loginUrl;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getEmailType() {
        return emailType;
    }

    public String getLoginUrl() {
        return loginUrl;
    }
}
