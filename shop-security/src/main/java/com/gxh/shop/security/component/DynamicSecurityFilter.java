package com.gxh.shop.security.component;

import com.gxh.shop.security.config.UrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



/**
 * 动态权限过滤器,基于路径动态权限过滤
 * @author gxh
 */

@Slf4j
public class DynamicSecurityFilter  extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Autowired
    private UrlsConfig urlsConfig;




    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest,servletResponse,filterChain);
        //options直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())){
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(),filterInvocation.getResponse());
            return;
        }

        //白名单放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : urlsConfig.getUrls()){

            if (pathMatcher.match(path, request.getRequestURI())){
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(),filterInvocation.getResponse());
                return;
            }
        }

        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
        

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
