package com.hr.companion.api.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth/")
@AllArgsConstructor
public class UserController {
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authManager;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse res) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String accessToken = userDetailsService.authenticateUser(request.getUsername(), request.getPassword(), res);
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User newUser = userDetailsService.registerUser(request);
        return ResponseEntity.ok(newUser);
    }
}
