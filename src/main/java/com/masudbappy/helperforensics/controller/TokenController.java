package com.masudbappy.helperforensics.controller;

import com.masudbappy.helperforensics.exceptions.UserException;
import com.masudbappy.helperforensics.models.User;
import com.masudbappy.helperforensics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

    private final UserService userService;

    @Autowired
    public TokenController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/media/resgistered_token/", method = RequestMethod.GET)
    @ResponseBody
    public User userVarificationFromGoogle(@RequestParam(value = "token", required = true) String token) throws Exception {
        try {
            System.out.println("Received Token");
            return userService.saveUser(token);
        } catch (Exception ex) {
            throw new UserException(("User can not save to the database"), ex);
        }
    }
}
