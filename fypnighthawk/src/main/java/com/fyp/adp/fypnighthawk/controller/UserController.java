package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.basedata.user.entity.UserProfile;
import com.fyp.adp.basedata.user.service.UserService;
import com.fyp.adp.common.ro.Result;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/current/get")
    public UserProfile getCurrentUserProfile(String token) {
        return userService.getCurrentUser(token);
    }
}
