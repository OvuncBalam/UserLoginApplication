package com.Digitus.repository;

import com.Digitus.domain.User;
import com.Digitus.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNameAndPassword(String userName, String password);

    User findByUserName(String userName);

    User findByEmail(String email);

    User findByVerificationCode(VerificationCode code);

}
