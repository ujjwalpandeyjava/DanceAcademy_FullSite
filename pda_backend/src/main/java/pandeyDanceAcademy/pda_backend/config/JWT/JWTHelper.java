package pandeyDanceAcademy.pda_backend.config.JWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import pandeyDanceAcademy.pda_backend.global.constants.Const;

@Component
public class JWTHelper {
	Logger logger = LoggerFactory.getLogger(JWTHelper.class);
	private final String JWT_SERVER_SECRETE_KEY_STRING="abcdefghijklThisIsServerKeythisisanotherkeywithsomeamazing";

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject); // Claims.SUBJECT
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration); // Claims.EXPIRATION
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
//		logger.info("claims {}", claims);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String sub) {
		LocalDateTime currentUTCTime = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime futureUTCTime = currentUTCTime.plusSeconds(Const.JWT_TOKEN_VALIDITY);

		String compact = Jwts.builder()
				.claims(claims)
				.subject(sub)
				.issuedAt(Date.from(currentUTCTime.toInstant(ZoneOffset.UTC)))
				.expiration(Date.from(futureUTCTime.toInstant(ZoneOffset.UTC)))
				.signWith(getSignKey())
				.compact();
		logger.warn("New token: {}", compact);
		return compact;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUsernameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// it generates a random secrete key everytim hit.
	private SecretKey getSignKey() {
		byte[] secretKeyBytes = JWT_SERVER_SECRETE_KEY_STRING.getBytes();
        return new SecretKeySpec(secretKeyBytes, "HmacSHA384");
//		return SIG.HS256.key().build();
	}
}
