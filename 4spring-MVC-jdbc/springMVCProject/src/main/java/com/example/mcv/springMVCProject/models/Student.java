package com.example.mcv.springMVCProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

//@JsonIgnoreProperties({"dept"})
public class Student {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("empI")
    private int empId;
    private String phone;
//    @JsonIgnore
    private String dept;

    public Student(String name, int empId, String phone, String dept) {
        this.name = name;
        this.empId = empId;
        this.phone = phone;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    @JsonIgnore
    @JsonProperty("dept")
    public String getDept() {
        return dept;
    }
    @JsonIgnore(value = true)
//    @JsonProperty
    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", empId=" + empId +
                ", phone='" + phone + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}
