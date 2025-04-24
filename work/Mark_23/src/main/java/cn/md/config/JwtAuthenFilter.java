package cn.md.config;

import cn.md.utils.JwtUtils;
import cn.md.utils.PackJsn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenFilter extends BasicAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtAuthenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        logger.debug("启用！！！ 授权过滤器- 构造函数");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.debug("业务请求: {}", request.getServletPath());

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            handleNoToken(request, response, chain);
            return;
        }

        String token = bearerToken.substring(BEARER_PREFIX.length());
        if (!isValidToken(token, response)) {
            return;
        }

        try {
            authenticateUser(token);
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleAuthenticationError(response, e);
        }
    }

    private void handleNoToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("没有 Token, 可能访问 /api/public");
        logger.info("无需认证对象（账号，权限），直接放行");
        chain.doFilter(request, response);
    }

    private boolean isValidToken(String token, HttpServletResponse response) throws IOException {
        try {
            if (!JwtUtils.isValid(token)) {
                logger.info("有 Token, 但是校验失败");
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Token 非法");
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("Token 校验过程中发生异常", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误");
            return false;
        }
    }

    private void authenticateUser(String token) {
        String username = JwtUtils.getUsername(token);
        List<String> roles = JwtUtils.getRoles(token);

        if (roles == null || roles.isEmpty()) {
            throw new SecurityException("Token 中没有包含角色信息，无法创建认证对象");
        }

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User user = new User(username, "", authorities);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleAuthenticationError(HttpServletResponse response, Exception e) throws IOException {
        logger.error("创建认证对象过程中发生异常", e);
        sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误");
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=utf-8");
        String ret = PackJsn.packM(status, message, "");
        response.getWriter().write(ret);
        response.flushBuffer();
    }
}
