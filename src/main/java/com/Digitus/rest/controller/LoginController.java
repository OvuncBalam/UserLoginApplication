package com.Digitus.rest.controller;

import com.Digitus.domain.User;
import com.Digitus.rest.request.LoginRequest;
import com.Digitus.rest.request.RegistrationRequest;
import com.Digitus.rest.response.LoginResponse;
import com.Digitus.rest.response.RegistrationResponse;
import com.Digitus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(name = "/api/login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest request) {

        final LoginResponse response = new LoginResponse();

        try {
            final User user = userService.findByUserNameAndPassword(request.getUserName(), request.getPassword());
            if (user != null) {
                response.setSuccess(true);

                return response;
            } else {
                response.setSuccess(false);
                response.setErrorMessage("User could not be found");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());

            return response;
        }

        return response;
    }



}
