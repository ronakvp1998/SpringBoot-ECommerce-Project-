package com.example.first.TodoApplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "lcwd.config")
@Component
public class LCWDConfig {

    private String name;
    public String password;
    private String email;
    private int emailExpTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmailExpTime() {
        return emailExpTime;
    }

    public void setEmailExpTime(int emailExpTime) {
        this.emailExpTime = emailExpTime;
    }

    @Override
    public String toString() {
        return "LCWDConfig{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", emailExpTime=" + emailExpTime +
                '}';
    }
}
