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

/**
 * Jwt认证入口点类，用于处理未通过认证的请求
 */
public class JwtAuthEntryPoint
        implements AuthenticationEntryPoint {
    // 日志记录器
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 构造函数
     */
    public JwtAuthEntryPoint() {
        logger.info("启用！！！ JwtAuthEntryPoint 构造函数");
    }

    /**
     * 处理未通过认证的请求
     *
     * @param request       HTTP请求对象
     * @param response      HTTP响应对象
     * @param authException 认证异常对象
     * @throws IOException      如果发生I/O错误
     * @throws ServletException 如果发生Servlet错误
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 记录认证异常信息
        logger.info(authException.getMessage());
        // 设置响应内容类型和状态码
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(403);
        // 记录警告日志
        logger.warn("登录访问权限异常...");
        // 将错误信息打包成JSON字符串
        String str = PackJsn.packM(403, "登录访问权限异常...", "");
        // 将JSON字符串写入响应并刷新缓冲区
        response.getWriter().write(str);
        response.flushBuffer();
    }
}
