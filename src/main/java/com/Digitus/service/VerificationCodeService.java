package com.Digitus.service;

import com.Digitus.domain.VerificationCode;

public interface VerificationCodeService {

    VerificationCode findVerificationCodeByCode(String code);
}
