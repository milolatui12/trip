package tritnt.trip.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String secretKeyAccessToken = System.getenv("JWT_SECRET_KEY");

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 *7;

    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyAccessToken));

    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)) // 1 giờ
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String generateRefreshToken(String token) {
        return Jwts.builder()
                .setSubject(token)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String extractUser(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    public String extractRefreshToken(String token) {
          return  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();

    }
}
