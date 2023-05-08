package com.bank.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(value=Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName="RequestCachingFilter", urlPatterns="/*")
public class RequestCachingFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(RequestCachingFilter.class);

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain
    ) throws ServletException, IOException {
        logger.debug("BEFORE REQUEST: url = {}, method = {}", request.getRequestURI(), request.getMethod());
        filterChain.doFilter(request, response);
        if (response.getStatus() == HttpStatus.OK.value()) {
            logger.info("REQUEST OK: url = {}, method = {}", request.getRequestURI(), request.getMethod());
        }
        logger.debug("AFTER REQUEST: url = {}, method = {}", request.getRequestURI(), request.getMethod());
    }
}
