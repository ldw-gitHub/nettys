package com.framework.model.base;

/**
 * @description
 * @author: liudawei
 * @date: 2020/5/11 10:47
 */
public class UserInfo {

    private Long userId;
    private String username;
    private String account;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
