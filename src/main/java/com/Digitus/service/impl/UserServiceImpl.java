package com.Digitus.service.impl;

import com.Digitus.domain.User;
import com.Digitus.domain.VerificationCode;
import com.Digitus.repository.UserRepository;
import com.Digitus.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByUserNameAndPassword(String userName, String password) {
        final User user = repository.findByUserNameAndPassword(userName, password);

        return user;
    }

    @Override
    public void insertUser(User user) {

       repository.save(user);
    }

    public User findByUserName(String userName) {
        final User user = repository.findByUserName(userName);

        return user;
    }

    public User findByEmail(String email) {
        final User user = repository.findByEmail(email);

        return user;
    }

    public User findByCode(VerificationCode code) {
        final User user = repository.findByVerificationCode(code);

        return user;
    }



}
