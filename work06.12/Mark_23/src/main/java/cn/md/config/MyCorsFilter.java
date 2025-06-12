package cn.md.config;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)  //把过滤器前置，优先级最高, 这样放行跨域访问
public class MyCorsFilter implements Filter {

    public MyCorsFilter() {
        System.out.println("MyCorsFilter init");
    }
    @Override
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
    // 将ServletRequest转换为HttpServletRequest，以便获取请求路径信息
    HttpServletRequest request = (HttpServletRequest) req;
    // 打印跨域请求的路径，用于调试或日志记录
    System.out.println("跨域请求路径:" + request.getServletPath());

    // 将ServletResponse转换为HttpServletResponse，以便设置响应头
    HttpServletResponse response = (HttpServletResponse) resp;
    // 设置响应头，允许所有域进行跨域请求
    response.setHeader("Access-Control-Allow-Origin", "*");
    // 允许所有方法进行跨域请求
    response.setHeader("Access-Control-Allow-Methods", "*");
    // 设置跨域请求预检结果的缓存时间
    response.setHeader("Access-Control-Max-Age", "3600");
    // 设置允许的跨域请求头
    response.setHeader("Access-Control-Allow-Headers",
            "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

    // 如果请求方法是OPTIONS，则直接返回状态码200，表示请求被成功接收并处理
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        response.setStatus(HttpServletResponse.SC_OK);
    } else {
        // 对于其他请求方法，继续执行链中的下一个过滤器或Servlet
        chain.doFilter(req, resp);
    }
}


    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

}