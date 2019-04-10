package com.bilibili.growing.filter;

/**
 * Created by liuguodong on 2017/8/1.
 */

import org.apache.catalina.connector.RequestFacade;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "HttpServletRequestReplacedFilter", urlPatterns = "/*")
public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        BiliLog.info("servelet filter start");
        //Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ServletRequest requestWrapper = null;

        String url = ((RequestFacade) request).getRequestURI();
        if(url.startsWith("/paymng/files")) {
            chain.doFilter(request, response);
        }
        else {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {
//        BiliLog.info("servelet filter stop");
        //Do nothing
    }

}
