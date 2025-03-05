package tritnt.trip.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tritnt.trip.exception.InvalidTokenException;
import tritnt.trip.model.CustomUserDetails;
import tritnt.trip.services.CustomUserDetailsService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, InvalidTokenException {
        logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

        final String authorizationHeader = request.getHeader("Authorization");

        String authToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authToken = authorizationHeader.substring(7);
        }

        if (authToken != null) {
            if (!jwtUtil.validateToken(authToken)) {
                throw new InvalidTokenException("Invalid or expired token!");
            }
            String userId = jwtUtil.extractUser(authToken);

            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
