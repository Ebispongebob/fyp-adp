package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.basedata.user.service.AuthService;
import com.fyp.adp.common.ro.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author MuxBwf
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping("/login")
    public Result login(@RequestParam String account, @RequestParam String password) {
        return Result.success(authService.doLogin(account, password));
    }
}
