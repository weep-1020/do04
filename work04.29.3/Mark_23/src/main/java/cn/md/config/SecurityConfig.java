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


@Configuration   // 将 SecurityConfig 创建对象 --->IOC
@EnableWebSecurity
//请求先到 SecurityConfig 过滤一下， 才会到控制器
//如果登录请求， 就会找UserDetailsService 的 Bean
public class SecurityConfig {  //se-cu-ri-ty 安全
    public SecurityConfig() {
        System.out.println("SecurityConfig 创建对象");
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //获取认证管理器， 简单而言就是比对密码：
    // 1. 主动调用 loadUserByUsername( ...)  访问 t_user
    // 2. 主动比对账号密码
    //        - 成功: successfulAutentication
    //        - 不成功: unsuccessfulAuthentication
    @Autowired
    AuthenticationConfiguration authenConfig;
    //获取认证管理器的配置类， 再去获取认证管理器

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        logger.info("启用！！！ 获取认证管理器  authenticationManager()");
        return authenConfig.getAuthenticationManager();
    }

    //There is no PasswordEncoder mapped for the id "null"
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  //加盐加密
    }

    //权限
    @Bean  // http.build() 返回值装配到 IOC 容器
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //权限判断， 是在过滤器的最后一环执行
        http.authorizeRequests()
                .antMatchers("/api/public/**").permitAll()  //无需登录，就可以访问的
                .antMatchers("/static/**", "/assets/**", "/*.html").permitAll()  //无需登录，就可以访问的
                .antMatchers("/api/login").permitAll()  //无需登录，就可以访问的
                .antMatchers("/go2login").permitAll()  //无需登录，就可以访问的
                .antMatchers("/api/adm/**")
                .hasAuthority("adm")
                .antMatchers("/api/fore/tea/**")
                .hasAuthority("tea")
                .antMatchers("/api/fore/stu/**")
                .hasAuthority("stu")
                .anyRequest().authenticated()   //authened 已经登录成功后才能访问 /api/user/改密码
                .and()
                .formLogin()   //  /login
                .permitAll()   //  无需登录可以访问（permit 允许）
                .and()
                .csrf().disable()
                //在这个后面加  不走 session, 我要走自己方式 (Token)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter( new JwtLoginFilter(authenConfig.getAuthenticationManager()))
                //添加自己的登录方式(api/login2, 并且传递 认证管理器
                .addFilter( new JwtAuthenFilter(authenConfig.getAuthenticationManager()))
                .exceptionHandling().authenticationEntryPoint(new JwtAuthEntryPoint());
        //添加自己的授权过滤器, 负责拦截每次业务请求
        return http.build();
    }
}
