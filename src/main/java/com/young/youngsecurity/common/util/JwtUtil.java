package com.young.youngsecurity.common.util;

import com.young.youngsecurity.common.security.AuthCacheServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/19
 */
public final class JwtUtil {

    // TOKEN的有效期1小时（S）
    private static final int TOKEN_TIME_OUT_HOUR = 1 * 3600 * 1000;

    // 加密KEY
    private static final String TOKEN_SECRET = "mySecret";

    /**
     * 生成jwt
     *
     * @param paramMap
     * @return
     */
    public static String generateJwt(Map<String, Object> paramMap) {
        Date date = new Date(System.currentTimeMillis() + TOKEN_TIME_OUT_HOUR);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET) //指定加密算法
                .setClaims(paramMap) //写入数据
                .setExpiration(date) //失效时间
                .compact();
    }

    /**
     * 获取Token中的claims信息
     */
    public static Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 是否有效 true-有效，false-失效
     */
    public static boolean verifyToken(String token) {
        if (StringUtils.hasText(token)) {
            return false;
        }
        try {
            getClaims(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Claims claims = getClaims("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODcxNjQ0MzMsImlkIjozfQ.oPxBeGjqn_waRBe6v0dEpSgfQGs29g2QuZEaJe_kaN_u6QzkzygGyU2B71uujTFKTIFPKFCD9Bc3cwYAD_CytA");
        System.err.println(claims.getExpiration());
        System.err.println(claims.get(AuthCacheServiceImpl.SUBJECT_KEY));
    }

}
