package com.masudbappy.helperforensics.controller;

import com.masudbappy.helperforensics.models.User;
import com.masudbappy.helperforensics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public List<User> getUsers() {
//        LOG.info("Saving user.");
        return userService.getUser();//userRepository.save(user);
    }

}
