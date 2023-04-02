package cn.hbmz.ruiji.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;

import cn.hbmz.ruiji.common.R;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest newRequest = (HttpServletRequest) request;
        HttpServletResponse newResponse = (HttpServletResponse) response;
        // 获取请求路径，判断这些路径是否需要被拦截
        StringBuffer requestURL = newRequest.getRequestURL();
        String[] checkURI = new String[] {
                "/backend/",
                "/front/",
                "/employee/login",
                "/employee/logout"
        };
        boolean check = check(checkURI, requestURL);
        if (check) {
            // 输出不需要拦截的路径
            log.info("本次请求不需要处理{}", requestURL);
            chain.doFilter(newRequest, newResponse);
            return;
        } else {
            // 判断登录状态
            if (newRequest.getSession().getAttribute("employee") != null) {
                chain.doFilter(newRequest, newResponse);
                return;
            }
            log.info("拦截到请求： {}", newRequest.getRequestURI()); // 检查拦截路径
            newResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return;
        }
    }

    private boolean check(String[] urls, StringBuffer requestURI) {
        for (String url : urls) {
            if (requestURI.indexOf(url) != -1)
                return true;
        }
        return false;
    }
}
