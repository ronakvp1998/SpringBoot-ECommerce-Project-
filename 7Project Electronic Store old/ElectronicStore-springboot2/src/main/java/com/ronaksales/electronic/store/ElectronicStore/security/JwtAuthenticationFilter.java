package com.ronaksales.electronic.store.ElectronicStore.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization
        String requestHeader =  request.getHeader("Authorization");
        logger.info("Header: {}",requestHeader);

        // Token:- Bearer qwdq3214234132e1asdasxcasdsad123
        String username = null;
        String token = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer")){
            token = requestHeader.substring(7);
            try{
                username = this.jwtHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException ex){
                logger.info("IllegalArgumentException");
                ex.printStackTrace();
            }catch (ExpiredJwtException ex){
                logger.info("ExpiredJwtException");
                ex.printStackTrace();
            }catch (MalformedJwtException ex){
                logger.info("MalformedJwtException");
                ex.printStackTrace();
            }catch (Exception ex){
                logger.info("Exception");
                ex.printStackTrace();
            }

        }else{
            logger.info("Invalid Header value !!");
        }

        //
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // fetch user details from ussername
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validToken =  this.jwtHelper.validateToken(token,userDetails);
            if (validToken){
                // set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else{
                logger.info("Validation fails !!");
            }
        }

        filterChain.doFilter(request,response);
    }
}
