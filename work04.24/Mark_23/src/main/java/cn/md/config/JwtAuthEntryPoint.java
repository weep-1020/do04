package cn.md.config;

import cn.md.utils.PackJsn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthEntryPoint
        implements AuthenticationEntryPoint {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public JwtAuthEntryPoint() {
        logger.info("启用！！！ JwtAuthEntryPoint 构造函数");
    }
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.info(authException.getMessage());
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(403);  //设置状态码
        //@ResponseBody 实现原理， 自己把对象转成JSON 字符串， 再用 response 响应浏览器
        // msg, data  code 3 个成员变量
        logger.warn("登录访问权限异常...");
        String str = PackJsn.packM(403, "登录访问权限异常...", "");
        response.getWriter().write(str);
        response.flushBuffer();
    }
}
