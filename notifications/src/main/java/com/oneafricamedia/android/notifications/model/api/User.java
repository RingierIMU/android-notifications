package com.oneafricamedia.android.notifications.model.api;

import java.util.Date;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String domain;
    private List<String> additional_domains;
    private String mobile_number;
    private Date created_at;
    private Date updated_at;
    private String last_login_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getAdditional_domains() {
        return additional_domains;
    }

    public void setAdditional_domains(List<String> additional_domains) {
        this.additional_domains = additional_domains;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getLast_login_at() {
        return last_login_at;
    }

    public void setLast_login_at(String last_login_at) {
        this.last_login_at = last_login_at;
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public String nameString() {
        return firstname + " " + lastname;
    }
}
