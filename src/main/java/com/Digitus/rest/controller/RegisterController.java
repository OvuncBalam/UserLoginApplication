package com.Digitus.rest.controller;

import com.Digitus.domain.User;
import com.Digitus.domain.VerificationCode;
import com.Digitus.rest.request.RegistrationRequest;
import com.Digitus.rest.response.RegistrationResponse;
import com.Digitus.rest.response.VerificationResponse;
import com.Digitus.service.UserService;
import com.Digitus.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RestController
public class RegisterController {
    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    VerificationCodeService verificationCodeService;

    @RequestMapping(value = "/api/Register", method = RequestMethod.POST)
    public RegistrationResponse Register(@RequestBody RegistrationRequest request) {

        final RegistrationResponse response = new RegistrationResponse();
        try {
            final User userByUserName = userService.findByUserName(request.getUserName());
            final User userByEmail = userService.findByEmail(request.getEmail());

            if(userByEmail != null || userByUserName != null) {
                response.setSuccess(false);
                response.setErrorMessage("Username or email already exists");

                return response;
            }

            if(!request.getPassword().equals(request.getConfirmPassword())) {
                response.setSuccess(false);
                response.setErrorMessage("passwords must match");

                return response;
            }

            final User user = new User();
            final VerificationCode verificationCode = new VerificationCode();
            verificationCode.setCode(verificationCode.generateVerCode());

            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(request.getPassword());
            user.setVerificationCode(verificationCode);

            userService.insertUser(user);
            sendEmailToSignedUpUser(user.getEmail(), user.getVerificationCode().getCode());

            response.setSuccess(true);

            return response;
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());

            return response;
        }

    }

    @RequestMapping(value="api/verifyEmail", method = RequestMethod.GET)
    public VerificationResponse verifyEmail(@RequestParam(value = "token") String token) {
        final VerificationResponse response = new VerificationResponse();
        try {
            final VerificationCode verificationCode =  verificationCodeService.findVerificationCodeByCode(token);
            if(verificationCode == null) {
                response.setSuccess(false);
                response.setErrorMessage("No account found for this token");

                return response;
            }

            final User userByToken = userService.findByCode(verificationCode);
            if(userByToken == null) {
               response.setSuccess(false);
               response.setErrorMessage("No account found for this token");

               return response;
            }
            userByToken.setActive(true);
            userService.insertUser(userByToken);

            response.setSuccess(true);

            return response;
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());

            return response;
        }
    }

    public void sendEmailToSignedUpUser(final String userEmail, final String token) throws Exception {
        try{
            final MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(new InternetAddress("ovuncbalam@hotmail.com"));
            final MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(userEmail);
            helper.setSubject("App Account Activation");
            helper.setText("<p>Please activate your account by clicking the link <a>http://localhost:8080/api/verifyEmail?token=%22+token+"+"</a></p>");

            mailSender.send(msg);
        } catch (Exception e) {
            throw new Exception("mail could not be sent");
        }



    }
}
