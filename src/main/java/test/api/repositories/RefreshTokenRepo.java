package test.api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import test.api.entities.RefreshToken;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
