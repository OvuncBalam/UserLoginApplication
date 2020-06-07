package com.Digitus.service.impl;

import com.Digitus.domain.VerificationCode;
import com.Digitus.repository.UserRepository;
import com.Digitus.repository.VerificationCodeRepository;
import com.Digitus.service.VerificationCodeService;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    VerificationCodeRepository repository;

    public VerificationCodeServiceImpl(VerificationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public VerificationCode findVerificationCodeByCode(String code) {
        VerificationCode verificationCode = repository.findByCode(code);

        return verificationCode;
    }
}
