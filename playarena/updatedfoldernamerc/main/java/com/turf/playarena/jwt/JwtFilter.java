package com.turf.playarena.jwt;

import java.io.IOException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    Logger logger = LoggerFactory.getLogger(getClass());
    //private String token = null;

    //   @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        logger.info("entered doFilter internal");
//        String authorizationHeader = request.getHeader("Authorization");
//        String userName = null;
//        try {
//            //	System.out.println("entered try ");
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                token = authorizationHeader.substring(7);
//                userName = jwtUtil.getUsernameFromToken(token);
//
//                logger.info("userName :" + userName);
//            }
//            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = jwtAuthenticationProvider.loadUserByUsername(userName);
//                logger.info("user details in JWTFilter ===============>"+userDetails);
//                if (jwtUtil.isTokenExpired(token)) {
//                    System.out.println("token is expired");
//                } else if (jwtUtil.validateToken(token, userDetails)) {
//                    logger.info("entered else if ");
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    logger.info("usernamePasswordAuthenticationToken =============>"+usernamePasswordAuthenticationToken);
//                    usernamePasswordAuthenticationToken
//                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                    logger.info("usernamePasswordAuthenticationToken after setting =============>"+usernamePasswordAuthenticationToken);
//                }
//            }
//        } catch (Exception ex) {
//            //System.out.println("entered catch");
//            if (ex.getMessage().contains("JWT expired at")) {
//                System.out.println("jwt expired");
//                // throw new
//                // JwtExpirationException(Response.Status.BAD_GATEWAY,ResponseConstants.JWT_EXPIRATION_MESSAGE);
//
//            }
//
//        }
//        filterChain.doFilter(request, response);
//    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtAuthenticationProvider.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);

    }

}
