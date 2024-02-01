package com.aspen.core.security.jwt;

import com.aspen.core.security.constants.ConstantUtil;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class JwtUtils {

    public static final String SECRET = "abcdefghabcdefghabcdefghabcdefgh";

    /**
     * 生成JWT
     */
    public static String generateToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * ConstantUtil.EXPIRE);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 解析JWT
     */
    public static Claims getClaimsByToken(String jwt) {
        try {
            if (jwt.startsWith(ConstantUtil.BEARER)) {
                jwt = jwt.replace(ConstantUtil.BEARER, "");
            }
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断JWT是否过期
     */
    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}