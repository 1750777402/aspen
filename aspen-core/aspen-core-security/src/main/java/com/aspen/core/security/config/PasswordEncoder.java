package com.aspen.core.security.config;

import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor
public class PasswordEncoder extends BCryptPasswordEncoder {

    //判断从前端接收的密码与数据库中的密码是否一致
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 接收到的前端的密码
        String pwd = rawPassword.toString();
        // 进行rsa解密
        try {
//            pwd = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, pwd);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
        if (encodedPassword != null && encodedPassword.length() != 0) {
            return BCrypt.checkpw(pwd, encodedPassword);
        } else {
            return false;
        }
    }
}
