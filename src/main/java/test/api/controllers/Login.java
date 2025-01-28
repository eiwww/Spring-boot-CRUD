package test.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import test.api.components.JwtService;
import test.api.entities.RefreshToken;
import test.api.models.AuthRequest;
import test.api.models.RefreshTokenRequest;
import test.api.services.RefreshTokenService;


@RestController
public class Login {
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authLogin(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        if(authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.username());
            String accessToken = jwtService.GenerateToken(authRequest.username());
            // return jwtService.GenerateToken(authRequest.username());
            // Build the response map
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("token", refreshToken.getToken());

            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String, String>> createRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.token())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.GenerateToken(user.getUsername());
                    Map<String, String> response = new HashMap<>();
                    response.put("accessToken", accessToken);
                    response.put("token", refreshTokenRequest.token());
                    return ResponseEntity.ok(response);
                }).orElseThrow(() -> new RuntimeException("Autherize error"));
    }
    
}
