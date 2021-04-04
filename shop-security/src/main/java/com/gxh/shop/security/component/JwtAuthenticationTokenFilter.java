package com.gxh.shop.security.component;

import com.gxh.shop.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gxh
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(this.tokenHeader);
//        判断请求头是否为空并且请求头是否以Bearer 开头
        if (header != null && header.startsWith(this.tokenHead)){
            //获取以Bearer 之后的部分
            String authToken = header.substring(this.tokenHead.length());
            //通过工具类获取用户名
            String userName = jwtTokenUtil.getUserNameFromToken(authToken);
            //如果用户名不为空,且之前未在Security上下文中存储过信息
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //重新获取用户信息
                UserDetails userDetails =this.userDetailsService.loadUserByUsername(userName);
                //使用jwt工具类进行信息比对
                if (jwtTokenUtil.validateToken(authToken,userDetails)){
                    //此构造函数仅应由对产生可信身份验证令牌感到满意的AuthenticationManager或AuthenticationProvider实现使用
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //创建新的身份验证详细信息
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //存储到Security上下文中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
