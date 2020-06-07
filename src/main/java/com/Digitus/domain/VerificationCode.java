package com.Digitus.domain;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name="verification_code")
public class VerificationCode {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    private String code;

    public static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    static Random random = new Random();

    public String generateVerCode() {
        StringBuffer vercode = new StringBuffer();
        for (int i = 0; i < 4; i++) {// generate 5 digit and character
            vercode.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return vercode.toString();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
       return generateVerCode();
    }

    public void setCode(String code) {
        this.code = code;
    }
}



