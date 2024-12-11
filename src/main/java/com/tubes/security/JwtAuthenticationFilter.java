// package com.tubes.security;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.lang.NonNull;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.tubes.service.UserService;
// import com.tubes.utils.jwtUtils;

// import java.io.IOException;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     @Autowired
//     private jwtUtils jwtUtils;

//     @Autowired
//     private UserService userService;

//     @Override
//     protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
//             throws ServletException, IOException {
//         String header = request.getHeader("Authorization");

//         if (header != null && header.startsWith("Bearer ")) {
//             String token = header.substring(7);
//             String username = jwtUtils.extractUsername(token);

//             if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                 UserDetails userDetails = userService.loadUserByUsername(username);
//                 if (jwtUtils.isTokenValid(token, userDetails.getUsername())) {
//                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                             userDetails, null, userDetails.getAuthorities());
//                     SecurityContextHolder.getContext().setAuthentication(authentication);
//                 }
//             }
//         }

//         chain.doFilter(request, response);
//     }
// }

