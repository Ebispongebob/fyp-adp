package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.basedata.user.entity.UserProfile;
import com.fyp.adp.basedata.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: UserController
 * @Author: ruitao.wei
 * @Date: 2023/2/17 18:29
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/get")
    public UserProfile getUserProfileById(Long id) {
        return userService.getUserProfileById(id);
    }
}
