package com.fyp.adp.basedata.user.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: UserEntity
 * @Author: ruitao.wei
 * @Date: 2023/2/17 18:09
 */
@Data
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "status")
    private String status;

}
