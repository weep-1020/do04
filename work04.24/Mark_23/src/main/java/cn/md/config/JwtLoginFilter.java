package cn.md.config;

import cn.md.utils.JwtUtils;
import cn.md.utils.PackJsn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//继承重写 SpringSecurity 已经存在的 账号密码认证过滤器
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    AuthenticationManager authenManger;
    //new JwtLoginFilter(authenConfig.getAuthenticationManager())

    public JwtLoginFilter() {
        System.out.println("启用！！！ 获取到认证管理器:" + authenManger);
        logger.debug("启用！！！ 获取到认证管理器:" + authenManger);
    }

    public JwtLoginFilter(AuthenticationManager manager) {
        logger.debug("获取到认证管理器:" + manager);
        this.authenManger = manager;
        //.loginProcessingUrl("/api/login")   //session 模式其效果， 因为重写， 这个不生效
        //在这里， 通知 SpringSecurtiy, 登录的请求路径。
        super.setFilterProcessesUrl("/api/login");  // super. 明确调用父对象方法
    }
    // 准备去认证  attempt  尝试  Authentication 认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            //获取浏览器登录的账号密码
            String username = obtainUsername(request);
            String pwd = obtainPassword(request);
            //request.getParameter("登录方式")  支持账号登录， 也支持短信验证码登录
            //System.out.println("登录的账号:" + username);
            logger.debug("登录的账号:" + username);
            //System.out.println("登录的密码:" + pwd)
            logger.debug("登录的密码:" + pwd);
            ;
            //loadUserByUsername(String username)
            //创建 springsecurity 内部的登录令牌
            UsernamePasswordAuthenticationToken  loginToten
                    = new UsernamePasswordAuthenticationToken(username, pwd);
            // 让 SpringSecurity 的登录（认证）管理器去认证
            // （认证）管理器去调用 loadUserByUsername(String username)
            authentication = authenManger.authenticate(loginToten);
            logger.debug("认证成功返回对象， session模式, 放在======>:");
            logger.debug(authentication.toString());
            logger.debug("<=======认证成功返回对象");
        } catch (AuthenticationException e) {
            logger.debug("登录失败",e);
        }
        return authentication;  // 返回登录成功的对象， 登录不成功没有对象
    }
    //多态: 继承 重写  向上转型  目的调用子类【对象】方法

    //authenManger.authenticate(loginToten);  认证成功调用如下方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        logger.debug("authenManger.authenticate(loginToten);  认证成功调用如下方法");
        //创建 Token
        //token= JwtUtil.createToken(账号, 角色);
        //  String token = JwtUtil.createToken("139001", roles);
        //再把 Token 分组成 JSON 响应 浏览器（ axios, apipost)
        logger.warn("登录成功=" + authResult);
        //登录成功对象， 里面有 username, 有角色
        logger.info(authResult.getPrincipal().toString());
        Object principal = authResult.getPrincipal();

        User user = (User) principal;  // 向下强制转换
        logger.info("登录成功=" + user.getUsername());
        logger.info(user.getAuthorities().toString());
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority sga : user.getAuthorities()) {
            logger.debug("sga.toString()=" + sga.toString());
            roles.add(sga.toString());
        }
        logger.info("roles=" + roles);
        String token = JwtUtils.createToken(user.getUsername(), roles);

        //map.put("token", token);
        Map<String, Object> tokenMap = new HashMap<>();
        System.out.println("token:"+token);
        tokenMap.put("token",token);

        String json =PackJsn.packM(201, "登录成功", tokenMap);
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        response.flushBuffer();
    }

    //authenManger.authenticate(loginToten);  认证失败调用如下方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        logger.debug("authenManger.authenticate(loginToten);  认证失败调用如下方法");
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(401);  //设置状态码
        //@ResponseBody 实现原理， 自己把对象转成JSON 字符串， 再用 response 响应浏览器
        // msg, data  code 3 个成员变量
        logger.warn("登录失败 401");
        String str = PackJsn.packM(401, "认证失败,账号或密码错误", "");
        response.getWriter().write(str);
        response.flushBuffer();
    }

}
