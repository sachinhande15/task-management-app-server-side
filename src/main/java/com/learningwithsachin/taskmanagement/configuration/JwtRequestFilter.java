package com.learningwithsachin.taskmanagement.configuration;

import com.learningwithsachin.taskmanagement.exception.InvalidTokenException;
import com.learningwithsachin.taskmanagement.exception.TokenExpiredException;
import com.learningwithsachin.taskmanagement.utilities.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static com.learningwithsachin.taskmanagement.utilities.ResponseHandler.setErrorResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            final String authHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(jwt, username)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, new ArrayList<>()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            logger.error("JWT Token has expired: {}", e.getMessage());
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT Token has expired");

        } catch (InvalidTokenException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
            setErrorResponse(response, HttpStatus.BAD_REQUEST, "JWT Token is invalid");

        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "An internal error occurred");
        }
    }
}
