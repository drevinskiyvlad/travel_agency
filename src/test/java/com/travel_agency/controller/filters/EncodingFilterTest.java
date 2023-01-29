package com.travel_agency.controller.filters;

import jakarta.servlet.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EncodingFilterTest {
    @InjectMocks
    private EncodingFilter encodingFilter;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private ServletRequest request;
    @Mock
    private ServletResponse response;
    @Mock
    private FilterChain chain;

    @BeforeEach
    void setUp() throws ServletException {
        when(filterConfig.getInitParameter("encoding")).thenReturn("UTF-8");
        encodingFilter.init(filterConfig);
    }

    @Test
    void testDoFilter() throws IOException, ServletException {
        when(request.getCharacterEncoding()).thenReturn(null);
        when(response.getCharacterEncoding()).thenReturn(null);

        encodingFilter.doFilter(request, response, chain);

        verify(request).setCharacterEncoding("UTF-8");
        verify(response).setCharacterEncoding("UTF-8");
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilter_EncodingAlreadySet() throws IOException, ServletException {
        when(request.getCharacterEncoding()).thenReturn("UTF-8");
        when(response.getCharacterEncoding()).thenReturn("UTF-8");

        encodingFilter.doFilter(request, response, chain);

        verify(request, never()).setCharacterEncoding("UTF-8");
        verify(response, never()).setCharacterEncoding("UTF-8");
        verify(chain).doFilter(request, response);
    }
}
