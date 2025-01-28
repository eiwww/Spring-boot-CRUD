package test.api.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.api.entities.RefreshToken;
import test.api.repositories.RefreshTokenRepo;
import test.api.repositories.UserRepo;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Autowired
    UserRepo userRepo;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
            .user(userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")))
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(180000))
            .build();
        return refreshTokenRepo.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again");
        }
        return token;
    }
}
