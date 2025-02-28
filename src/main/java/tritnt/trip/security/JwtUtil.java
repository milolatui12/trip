package tritnt.trip.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String secretKeyAccessToken = "BG/w12HoXuE0t745w+59dAHxfG8PhMy+LlXe+i/Jrv4=";
    private final String secretKeyRefreshToken = "0wPUu6Ba18k6VfDBrDLInmknE6zEFcKsayA3Lxb4iOM=";
    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 *7;

    private final Key accessTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyAccessToken));
    private final Key refreshTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyRefreshToken));

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)) // 1 giờ
                .signWith(SignatureAlgorithm.HS256, accessTokenKey)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, refreshTokenKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String username) {
        try {
            Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secretKeyAccessToken).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
