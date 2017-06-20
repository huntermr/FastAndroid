package demo.utils;//package cn.tbl.android.utils;
//
//import android.util.Base64;
//
//import java.util.Date;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
///**
// * Created by Administrator on 2017/5/26.
// */
//
//public class TokenUtil {
//    private static TokenUtil mInstance;
//
//    static{
//        System.loadLibrary("keyFile");
//    }
//
//    public static TokenUtil getInstance(){
//        if(mInstance == null){
//            mInstance = new TokenUtil();
//        }
//        return mInstance;
//    }
//
//    public static native String getKey();
//
//    /**
//     * 由字符串生成加密key
//     */
//    private static SecretKey generalKey() {
//        byte[] encodedKey = Base64.encode(getKey().getBytes(), Base64.NO_WRAP);
//        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//        return key;
//    }
//
//    /**
//     * 创建jwt
//     */
//    public static String createJWT(String subject, long ttlMillis) {
//
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//        SecretKey key = generalKey();
//        JwtBuilder builder = Jwts.builder()
//                .setIssuedAt(now)
//                .setSubject(subject)
//                .signWith(signatureAlgorithm, key);
//        if (ttlMillis >= 0) {
//            long expMillis = nowMillis + ttlMillis;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp);
//        }
//        return builder.compact();
//    }
//
//    /**
//     * 解密jwt
//     */
//    public static Claims parseJWT(String jwt) {
//        SecretKey key = generalKey();
//        Claims claims = Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(jwt)
//                .getBody();
//        return claims;
//    }
//
//    public static void main(String[] args) throws Exception {
//        String subject = "this is content";
//        Long ttlMillis = 3600L * 1000L;
//
//        String token = TokenUtil.createJWT(subject, ttlMillis);
//        System.out.println(token);
//
////        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJpYXQiOjE0OTU0MzQ5ODAsInN1YiI6IntcImlkXCI6MTAsXCJ1c2VybmFtZVwiOlwiZHhrXCIsXCJwYXNzd29yZFwiOlwiMTk5MTEyMTJcIn0iLCJleHAiOjE0OTU0MzQ5OTB9.Le9_xH29F6S-gWTpKEAIBrvjdfga7dy_LobVEYfXrng";
////        Claims claims = tokenUtil.parseJWT(token);
////        System.out.println(claims);
//    }
//}
