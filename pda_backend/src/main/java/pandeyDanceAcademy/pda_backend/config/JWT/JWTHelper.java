package pandeyDanceAcademy.pda_backend.config.JWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Component
public class JWTHelper {
	public static final long JWT_TOKEN_VALIDITY = 60; // in minutes

//	private String secret = "${JWT_SERVER_SECRETE_KEY}";

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject); // Claims.SUBJECT
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration); // Claims.EXPIRATION
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Retrieving any information from token we need the secret key.
	 * 
	 * @param token
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().verifyWith( getSignKey()).build().parseSignedClaims(token).getPayload();
	}

	/**
	 * Check is the token still valid?
	 * 
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}
	
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return doGenerateToken(claims, userDetails.getUsername());		
	}
	
	/**
	 * While creating the token
	 * 1. Define claims of the token, like issuer, expiration, subject, and the ID
	 * 2. Sign the JWT using HS512 algorithm secret key.
	 * 3. According to JWS compact serialization (https://tools.ietg.org/html/draft-
	 * compaction of the JWT to a URL-safe string.
	 * 
	 * 
	 * @param claims
	 * @param sub
	 * @return
	 */
	private String doGenerateToken(Map<String, Object> claims, String sub) {
        LocalDateTime currentUTCTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime futureUTCTime = currentUTCTime.plusMinutes(JWT_TOKEN_VALIDITY);

        return Jwts.builder()
        		.claims(claims)
        		.subject(sub)
        		.issuedAt(Date.from(currentUTCTime.toInstant(ZoneOffset.UTC)))
				.expiration(Date.from(futureUTCTime.toInstant(ZoneOffset.UTC)))
				.signWith(getSignKey(), SIG.HS512)
				.compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUsernameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private SecretKey getSignKey() { 
        // byte[] keyBytes = Decoders.BASE64.decode(SECRET); 
        // return Keys.hmacShaKeyFor(keyBytes); 
        return SIG.HS512.key().build();
    }
}
