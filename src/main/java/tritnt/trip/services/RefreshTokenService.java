package tritnt.trip.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tritnt.trip.model.RefreshToken;
import tritnt.trip.model.User;
import tritnt.trip.repositories.RefreshTokenRepository;
import tritnt.trip.repositories.UserRepository;
import tritnt.trip.security.JwtUtil;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public RefreshToken createRefreshToken(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        refreshTokenRepository.deleteByUserUserId(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("Wrong credentials");
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(604800));
        refreshToken.setUser(user.get());

        return refreshTokenRepository.save(refreshToken);
    }

    public String refreshAccessToken(String token) {
        Optional<RefreshToken> storedRefreshToken = refreshTokenRepository.findByToken(token);

        if (storedRefreshToken.isEmpty()) {
            throw new RuntimeException("Refresh token invalid");
        }

        if (storedRefreshToken.get().getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.deleteById(storedRefreshToken.get().getId());
            throw new RuntimeException("Refresh token expired");
        }

        User user = storedRefreshToken.get().getUser();

        return jwtUtil.generateAccessToken(user.getUserId());
    }
}
