package test.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test.api.entities.RefreshToken;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM refresh_token WHERE expiry_date < NOW()", nativeQuery = true)
    int deleteAllExpiredTokens();
}
