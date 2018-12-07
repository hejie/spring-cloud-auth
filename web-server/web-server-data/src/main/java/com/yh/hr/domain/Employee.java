package com.yh.hr.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "js_sys_employee")
public class Employee {

    //工号
    @Id
    private String empCode;
    //姓名
    private String empName;
    //机构/部门
    private String officeName;
    //状态
    @Column(length = 1)
    private String status;


}
