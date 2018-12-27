package com.highqi.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;


/**
 * @Author: 陈建春
 * @Date: 2018-12-26 21:09
 * @Description: JWT工具类
 */
@Data
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    //盐  (加密规则)
    private String salt;

    //过期时间
    private long expirationTime;

    /**
     * 生成JWT
     *
     * @param id
     * @param username
     * @return
     */
    public String createJWT(String id, String username, String role) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, salt).claim("role", role);
        if (expirationTime > 0) {
            builder.setExpiration(new Date(nowMillis + expirationTime));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(salt)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
