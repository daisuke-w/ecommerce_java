package d.com.ecommerce.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import d.com.ecommerce.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private long jwtExpirationMs;
	
	public String generateJwtToken(User user) {
		// JWT の構成情報を設定
		return Jwts.builder()
			.setSubject(user.getUsername())
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
			.signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
			.compact();
	}
	
	// トークンからユーザー名を取得
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret.getBytes())
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	// JWTトークンを検証
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			System.err.println("Invalid JWT signature: " + e.getMessage());
		} catch (MalformedJwtException e) {
			System.err.println("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.err.println("JWT token is expired: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.err.println("JWT token is unsupported: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.err.println("JWT claims string is empty: " + e.getMessage());
		}
		return false;
	}
	
	// リクエストからJWTトークンを抽出
	public String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// トークンが "Bearer " で始まるか確認
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // "Bearer " を除去
		}
		return null;
	}
}
