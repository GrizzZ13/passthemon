package com.backend.passthemon.utils.tokenutils;

import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JwtUtil {
    public static final Integer TOKEN_VERIFIED = 1;
    public static final Integer TOKEN_INVALID = 2;
    public static final Integer TOKEN_EXPIRED = 3;

    public static final long JWT_TTL_ACCESS = 60L * 1000 * 5; // 5 minutes
    public static final long JWT_TTL_REFRESH = 60L * 60 * 1000 * 24 * 14; // 2 weeks
//    public static final long JWT_TTL_REFRESH = 60L * 1000 * 2; // 2 weeks

    private static final String JWT_SECRET = "SJTUSE2021";
    private static final String JWT_ISSUER = "Grizz";

    public static Integer verify(String Jwt){
        Claims claims = null;
        try{
            SecretKey key = getSecretKey();
            claims =  Jwts.parser().setSigningKey(key).parseClaimsJws(Jwt).getBody();
        }
        catch (ExpiredJwtException e) {
            return TOKEN_EXPIRED;
        }
        catch (Exception e) {
            return TOKEN_INVALID;
        }
        return TOKEN_VERIFIED;
    }

    public static Integer getUserid(String Jwt) {
        Claims claims;
        try{
            SecretKey key = getSecretKey();
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(Jwt).getBody();
        }
        catch (ExpiredJwtException e) {
            return (Integer) e.getClaims().get("userid");
        }
        catch (Exception e) {
            return null;
        }
        return (Integer) claims.get("userid");
    }

    public static String getSubject(String Jwt) {
        Claims claims;
        try{
            SecretKey key = getSecretKey();
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(Jwt).getBody();
        }
        catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
        catch (Exception e) {
            return null;
        }
        return claims.getSubject();
    }

    private static SecretKey getSecretKey() {
        byte[] encodedKey = Base64.encodeBase64(JWT_SECRET.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static String generateJWT(String JwtId, String issuer, String subject, long ttlMillis, Map<String, Object> claims) throws Exception {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();

        // 生成签名的时候使用的秘钥secret，秘钥不可泄露
        SecretKey key = getSecretKey();

        // 下面就是在为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(JwtId)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(new Date(nowMillis))           // iat: jwt的签发时间
                .setIssuer(issuer)          // issuer：jwt签发人
                .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            builder.setExpiration(new Date(expMillis));
        }
        return builder.compact();
    }

    // generate access token by unique user id
    public static String generateAccessJWT(String subject, Integer userid) throws Exception {
        HashMap claims = new HashMap();
        claims.put("userid", userid);
        return generateJWT(UUID.randomUUID().toString(), JWT_ISSUER, subject, JWT_TTL_ACCESS, claims);
    }

    // generate refresh token by unique user id
    public static String generateRefreshJWT(String subject, Integer userid) throws Exception {
        HashMap claims = new HashMap();
        claims.put("userid", userid);
        return generateJWT(UUID.randomUUID().toString(), JWT_ISSUER, subject, JWT_TTL_REFRESH, claims);
    }
}
