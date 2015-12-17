package org.dspace.rest.authentication;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
