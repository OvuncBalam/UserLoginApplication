package com.Digitus.service;

import com.Digitus.domain.User;
import com.Digitus.domain.VerificationCode;

public interface UserService {

    User findByUserNameAndPassword(String userName, String password);

    void insertUser(User user);

    User findByUserName(String userName);

    User findByEmail(String email);

    User findByCode(VerificationCode code);

}
