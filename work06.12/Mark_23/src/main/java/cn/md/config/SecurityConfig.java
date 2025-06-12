package cn.md.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 配置Spring Security的安全设置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationConfiguration authenConfig;

    /**
     * 获取认证管理器
     *
     * @return 认证管理器对象
     * @throws Exception 可能抛出的异常
     */
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        logger.info("启用！！！ 获取认证管理器  authenticationManager()");
        return authenConfig.getAuthenticationManager();
    }

    /**
     * 创建密码编码器，用于密码加密
     *
     * @return 密码编码器对象
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 加盐加密
    }

    /**
     * 配置安全过滤链，设置权限控制和登录方式
     *
     * @param http HttpSecurity对象，用于配置安全设置
     * @return 配置好的SecurityFilterChain对象
     * @throws Exception 可能抛出的异常
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/public/**", "/static/**", "/assets/**", "/*.html", "/api/login", "/go2login")
            .permitAll()  // 无需登录即可访问的资源
            .antMatchers("/api/adm/**")
            .hasAuthority("adm")  // 需要'adm'权限
            .antMatchers("/api/fore/tea/**")
            .hasAuthority("tea")  // 需要'tea'权限
            .antMatchers("/api/fore/stu/**")
            .hasAuthority("stu")  // 需要'stu'权限
            .anyRequest().authenticated()  // 其他请求需要登录
            .and()
            .formLogin().permitAll()  // 登录页面无需登录即可访问
            .and()
            .csrf().disable()  // 禁用CSRF保护
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 不使用Session
            .and()
            .addFilter(new JwtLoginFilter(authenConfig.getAuthenticationManager()))  // 添加JWT登录过滤器
            .addFilter(new JwtAuthenFilter(authenConfig.getAuthenticationManager()))  // 添加JWT认证过滤器
            .exceptionHandling().authenticationEntryPoint(new JwtAuthEntryPoint());  // 设置未授权访问的处理方式
        return http.build();
    }
}
