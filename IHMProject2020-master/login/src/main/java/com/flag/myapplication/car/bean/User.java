package com.flag.myapplication.car.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "User")
public class User implements Serializable {
    @Column(name = "id", isId = true,autoGen=false)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "type")
    private Integer type;
    @Column(name = "zhuangt")
    private Integer zhuangt;
    @Column(name = "renzhange")
    private Integer renzhange;

    public Integer getRenzhange() {
        return renzhange;
    }

    public void setRenzhange(Integer renzhange) {
        this.renzhange = renzhange;
    }

    public Integer getZhuangt() {
        return zhuangt;
    }

    public void setZhuangt(Integer zhuangt) {
        this.zhuangt = zhuangt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}