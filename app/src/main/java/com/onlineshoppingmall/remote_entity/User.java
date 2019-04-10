package com.onlineshoppingmall.remote_entity;

public class User {
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private String mail;

    private String aids;

    private Integer aidP;

    private String gids;

    private String sids;

    private String oids;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getAids() {
        return aids;
    }

    public void setAids(String aids) {
        this.aids = aids == null ? null : aids.trim();
    }

    public Integer getAidP() {
        return aidP;
    }

    public void setAidP(Integer aidP) {
        this.aidP = aidP;
    }

    public String getGids() {
        return gids;
    }

    public void setGids(String gids) {
        this.gids = gids == null ? null : gids.trim();
    }

    public String getSids() {
        return sids;
    }

    public void setSids(String sids) {
        this.sids = sids == null ? null : sids.trim();
    }

    public String getOids() {
        return oids;
    }

    public void setOids(String oids) {
        this.oids = oids == null ? null : oids.trim();
    }
}