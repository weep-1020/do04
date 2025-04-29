package cn.md.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//工具类
public class JwtUtils {
    // token 的请求头字段
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    //签名的加密秘钥，只需要服务器知道就行了
    private static final String JWT_SECRET = "qwer1234";

    //Token 的时间
    private static final long EXPIRE_TIME = 120 * 60 * 1000;

    /**
     * 生成签名（Token），设置过期时间
     *
     * @param username 用户名，用于标识用户
     * @param roleList 用户角色列表，用于权限验证
     * @return 生成的Token字符串，如果生成失败则返回null
     */
    public static String createToken(String username, List<String> roleList) {
        // 计算Token的过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String roles = "";
        // 拼接角色信息
        for (String s : roleList) {
            roles = roles + s + ",";
        }
        try {
            // 创建HMAC256算法实例
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            // 构建Token
            return JWT.create()
                    .withClaim("username", username) // 将用户名存入Token
                    .withClaim("roles", roles) // 将用户角色存入Token
                    .withExpiresAt(expireDate) // 设置过期时间
                    .sign(algorithm); // 签名生成Token
        } catch (IllegalArgumentException e) {
            // 记录Token生成失败的错误日志
            logger.error("Token 生成失败", e);
            return null;
        }

    }

    /**
     * 检查Token是否有效（是否过期、是否可解析）
     *
     * @param token Token
     * @return 是否有效
     */
    public static boolean isValid(String token) {
        if (token != null & token.length() > 1) {
            try {
                //创建验证对象,这里使用的加密算法和密钥必须与生成TOKEN时的相同否则无法验证
                // 使用相同的加密算法和密钥验证 Token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
                //验证JWT
                DecodedJWT decodedJwt = jwtVerifier.verify(token);
                // 检查当前时间是否在 Token 的过期时间之前
                return new Date().before(decodedJwt.getExpiresAt());
            } catch (Exception e) {
                logger.error("Token 验证失败", e);
                return false;
            }
        } else {
            logger.debug("Token 是空的");
            return false;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username
     * @return 是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @param token 密钥
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取 Token 中包含的角色列表
     *
     * @param token
     * @return 角色列表
     */
    public static List<String> getRoles(String token) {
        List<String> roles = new ArrayList<>();
        try {
            DecodedJWT jwt = JWT.decode(token);
            String s = jwt.getClaim("roles").asString();

            // 按逗号分割角色信息
            String[] arr = s.split(",");
            for (String s2 : arr) {
                if (s2 != null && s2.length() > 0) {
                    roles.add(s2);
                }
            }
        } catch (JWTDecodeException e) {
            logger.warn("无法从 Token 中解析角色信息", e);
        }
        return roles;
    }
}
