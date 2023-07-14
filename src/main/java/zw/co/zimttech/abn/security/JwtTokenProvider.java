package zw.co.zimttech.abn.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zw.co.zimttech.abn.entity.User;
import zw.co.zimttech.abn.key.KeyGen;
import zw.co.zimttech.abn.response.Response;
import zw.co.zimttech.abn.response.TokenResponse;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;



    public TokenResponse generateToken(String subject,User user) {

        String issuer = "abn Authorization Service";
        String uniqueKey = String.valueOf(KeyGen.getUniqueId());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);



        String token = Jwts.builder()
                .setId(uniqueKey)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        TokenResponse tokenResponse = TokenResponse.builder()
                .id(user.getId())
                .token(token)
                .userName(user.getUsername())
                .success(true)
                .build();
        return tokenResponse;
    }

    public Response parseJWT(String jwt) {

        if (jwt == null) {
            return new Response().buildErrorResponse("Invalid Token Key");
        }

        Response response = validateToken(jwt);
        if (!response.isSuccess()) {
            return new Response().buildErrorResponse(response.getMessage());
        }
        return new Response().buildSuccessResponse(response.getMessage());
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Response validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

            return new Response().buildSuccessResponse("Valid token");
        } catch (WeakKeyException ex) {
            logger.error("Invalid authentication signature: " + ex.getMessage());
            return new Response().buildErrorResponse("Invalid authentication signature: " + ex.getMessage());
        } catch (SignatureException ex) {
            logger.error("Invalid authentication signature");
            return new Response().buildErrorResponse("Invalid authentication signature");
        } catch (MalformedJwtException ex) {
            return new Response().buildErrorResponse("Invalid authentication token");
        } catch (ExpiredJwtException ex) {
            return new Response().buildErrorResponse("Expired authentication token");
        } catch (UnsupportedJwtException ex) {
            return new Response().buildErrorResponse("Unsupported authentication token");
        } catch (IllegalArgumentException ex) {
            return new Response().buildErrorResponse("authentication claims string is empty.");
        }
    }
}
