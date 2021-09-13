package com.backend.passthemon.filter;

import com.backend.passthemon.wrapper.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 替换HttpServletRequest
 **/
@Slf4j
public class ReplaceStreamFilter implements Filter {
    private final String[] RELEASE_URL=new String[]{
            "/user/login",
            "/user/signup",
            "/user/reset",
            "/user/sendEmail",
            "/error",
            "/test"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String s1=((HttpServletRequest) request).getRequestURI();
        if(shouldRelease(s1)) {
            chain.doFilter(request,response);
        }
        else {
            ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {
        log.info("StreamFilter销毁...");
    }

    boolean shouldRelease(String url){
        for (String s : this.RELEASE_URL) {
            if (url.equals(s)) return true;
        }
        return false;
    }
}
