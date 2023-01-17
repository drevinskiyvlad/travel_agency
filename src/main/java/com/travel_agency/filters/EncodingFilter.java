package com.travel_agency.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

public class EncodingFilter implements Filter {
    String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if(req.getCharacterEncoding() == null)
            req.setCharacterEncoding(encoding);
        if(resp.getCharacterEncoding() == null)
            resp.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }
}
