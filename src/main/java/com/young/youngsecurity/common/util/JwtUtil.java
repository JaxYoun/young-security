package com.young.youngsecurity.common.util;

import com.young.youngsecurity.common.exception.MyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/19
 */
@Slf4j
public final class JwtUtil {

    /**
     * 一小时包含的毫秒数
     */
    private static final int MILLISECOND_PER_HOUR = 3600_000;

    /**
     * 短token的有效期1小时（S）
     */
    private static final int SHORT_TOKEN_TIME_OUT_HOUR = 2;

    /**
     * 长token的有效期1小时（S）
     */
    private static final int LONG_TOKEN_TIME_OUT_HOUR = 24;

    /**
     * 加密key
     */
    private static final String TOKEN_SECRET = "mySecret";

    /**
     * token目的
     */
    public static final String TOKEN_PURPOSE = "purpose";

    /**
     * token目的-刷新jwt
     */
    public static final String TOKEN_PURPOSE_REFRESH = "refreshToken";

    /**
     * 主体key
     */
    public static final String SUBJECT_KEY = "id";

    /**
     * 生成主jwt
     *
     * @param userId
     * @return
     */
    public static String generateMainJwt(Long userId) {
        Date expireAt = new Date(SHORT_TOKEN_TIME_OUT_HOUR * MILLISECOND_PER_HOUR + System.currentTimeMillis());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET) //指定加密算法
                .setIssuer("young")
                .setIssuedAt(new Date())
                .claim(SUBJECT_KEY, userId) //写入数据
                .setExpiration(expireAt) //失效时间
                .compact();
    }

    /**
     * 以主jwt为蓝本，生成副jwt（刷新token用）
     *
     * @param mainToken
     * @return
     */
    public static String generateAdditionFromMainJwt(String mainToken) {
        Claims claims = JwtUtil.getClaims(mainToken);
        Long userId = claims.get(SUBJECT_KEY, Long.class);
        Date expireAt = new Date(LONG_TOKEN_TIME_OUT_HOUR * MILLISECOND_PER_HOUR + System.currentTimeMillis());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET) //指定加密算法
                .setIssuer("young")
                .setIssuedAt(new Date())
                .claim(TOKEN_PURPOSE, TOKEN_PURPOSE_REFRESH)
                .claim(SUBJECT_KEY, userId)
                .setExpiration(expireAt)
                .compact();
    }

    /**
     * 以副jwt为蓝本，生成主jwt（刷新token用）
     *
     * @param additionToken
     * @return
     */
    public static String generateMainFromAdditionJwt(String additionToken) {
        Claims claims = JwtUtil.getClaims(additionToken);
        String tokenPurpose = (String) claims.get(TOKEN_PURPOSE);
        if (TOKEN_PURPOSE_REFRESH.equals(tokenPurpose)) {
            Long userId = claims.get(SUBJECT_KEY, Long.class);
            return generateMainJwt(userId);
        } else {
            throw new MyException(HttpStatus.PAYMENT_REQUIRED.value(), HttpStatus.PAYMENT_REQUIRED.getReasonPhrase());
        }
    }

    /**
     * 获取Token中的claims信息
     */
    public static Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new MyException(HttpStatus.PAYMENT_REQUIRED.value(), HttpStatus.PAYMENT_REQUIRED.getReasonPhrase());
        }
    }

    public static void main(String[] args) {
        Claims claims = getClaims("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODcxNjQ0MzMsImlkIjozfQ.oPxBeGjqn_waRBe6v0dEpSgfQGs29g2QuZEaJe_kaN_u6QzkzygGyU2B71uujTFKTIFPKFCD9Bc3cwYAD_CytA");
        System.err.println(claims.getExpiration());
        System.err.println(claims.get(SUBJECT_KEY));
    }

}
