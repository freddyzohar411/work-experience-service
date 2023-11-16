package com.avensys.rts.workexperienceservice.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import com.avensys.rts.workexperienceservice.util.JwtUtil;

import java.util.List;

/**
 * @author Kotaiah
 * This class is used to handle JWT Auth token validation.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Auth Pre-handling");

        // Get token from header from axios
        String authorizationHeader = request.getHeader("Authorization");

//
        log.info("Authorization Header: {}", authorizationHeader);
//
        // Check if token is present
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing Authorization Header");
        }

//        // Get the token string
        String token = authorizationHeader.substring(7);

        // Validate JWT with the public key from keycloak
        jwtUtil.validateToken(token);

        // validate with endpoint
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8090/api/user/validate?token=" + token;
//        System.out.println("AuthInterceptor URL: " + url);
//        AuthResponseDTO authResponse = restTemplate.getForObject(url, AuthResponseDTO.class);
//        System.out.println("AuthInterceptor Valid: " + authResponse.isActive());
//
//        if (!authResponse.isActive()) {
//            throw new RuntimeException("Invalid Token");
//        }

        // Get claims from token

        // Extract all claims from the signed token
        Claims claims = jwtUtil.extractAllClaims(token);

        // Extract out the email and roles from the claims
        String email = (String) claims.get("email");
        List<String> roles = jwtUtil.extractRoles(claims);

        // Store in request context to be used in services
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute("email", email, RequestAttributes.SCOPE_REQUEST);
            requestAttributes.setAttribute("roles", roles, RequestAttributes.SCOPE_REQUEST);
            requestAttributes.setAttribute("token", token, RequestAttributes.SCOPE_REQUEST);
        }

        return true; // Continue the request processing chain
    }

}


