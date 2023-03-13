package com.fyp.adp.fypnighthawk.service;

import com.fyp.adp.basedata.user.service.UserService;
import com.fyp.adp.fypnighthawk.AdpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: TODO
 * @Author: ruitao.wei
 * @Date: 2023/3/12 1:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdpApplication.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testGetCurrentUser() {
//        userService.getCurrentUser();
    }
}
