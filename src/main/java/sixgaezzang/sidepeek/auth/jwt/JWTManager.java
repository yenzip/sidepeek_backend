package sixgaezzang.sidepeek.auth.jwt;

import static sixgaezzang.sidepeek.auth.exception.message.AuthErrorMessage.TOKEN_IS_EXPIRED;
import static sixgaezzang.sidepeek.auth.exception.message.AuthErrorMessage.TOKEN_IS_INVALID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import sixgaezzang.sidepeek.common.exception.TokenValidationFailException;
import sixgaezzang.sidepeek.config.properties.JWTProperties;

@Component
public class JWTManager {

    private static final int MINUTE_PER_DAY = 24 * 60;
    private static final long MILLISECONDS_PER_MINUTE = 60 * 1000L;
    public static final String USER_ID_CLAIM = "user_id";

    private final String issuer;
    private final SecretKey secretKey;
    private final long expiredAfter;
    private final long refreshExpiredAfter;

    public JWTManager(JWTProperties jwtProperties) {
        issuer = jwtProperties.issuer();
        secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8));
        expiredAfter = jwtProperties.expiredAfter() * MILLISECONDS_PER_MINUTE;
        refreshExpiredAfter =
            jwtProperties.refreshExpiredAfter() * MINUTE_PER_DAY * MILLISECONDS_PER_MINUTE;
    }

    public String generateAccessToken(long userId) {
        return generateToken(userId, expiredAfter);
    }

    public String generateRefreshToken(long userId) {
        return generateToken(userId, refreshExpiredAfter);
    }

    public Long getUserId(String token) {
        return parseClaims(token).get(USER_ID_CLAIM, Long.class);
    }

    public Long getExpiredAt(String token) {
        return parseClaims(token).getExpiration().getTime();
    }

    private String generateToken(long userId, long expiredAfter) {
        Instant now = Instant.now();
        Instant expiredAt = now.plusMillis(expiredAfter);

        return Jwts.builder()
            .setIssuer(issuer)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expiredAt))
            .claim(USER_ID_CLAIM, userId)
            .signWith(secretKey)
            .compact();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenValidationFailException(TOKEN_IS_EXPIRED);
        } catch (Exception e) {
            throw new TokenValidationFailException(TOKEN_IS_INVALID);
        }
    }
}
