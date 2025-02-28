package tritnt.trip.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tritnt.trip.model.RefreshToken;
import tritnt.trip.model.User;
import tritnt.trip.repositories.RefreshTokenRepository;
import tritnt.trip.repositories.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new RuntimeException("Wrong credentials");
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(604800));
        refreshToken.setUser(user.get());

        return refreshTokenRepository.save(refreshToken);
    }
}
