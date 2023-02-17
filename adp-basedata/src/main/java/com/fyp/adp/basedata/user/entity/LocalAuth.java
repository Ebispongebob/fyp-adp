package com.fyp.adp.basedata.user.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: TODO
 * @Author: ruitao.wei
 * @Date: 2023/2/17 18:17
 */
@Data
@Table(name = "local_auth")
public class LocalAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long   id;
    @Column(name = "user_code")
    private String userCode;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_phone")
    private String userPhone;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "password")
    private String password;
}
