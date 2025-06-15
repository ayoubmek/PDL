package com.pdl.pdl.Configuration;

import com.pdl.pdl.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    // Check if Authorization header is present and starts with 'Bearer '
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      logger.debug("Authorization header missing or invalid");
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt = authHeader.substring(7);  // Extract the JWT token
    final String username = jwtService.extractUsername(jwt);  // Extract the username from the JWT

    // Proceed if the username is not null and no authentication exists in SecurityContext
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      // Load user details using the extracted username
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      // Validate the JWT token
      if (jwtService.isTokenValid(jwt, userDetails)) {
        // Create an authentication token
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );

        // Set the details for the authentication token
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the authentication token into the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // Continue the filter chain
    filterChain.doFilter(request, response);
  }
}
