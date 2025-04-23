package com.app.trading.Config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	
	private static final long EXPIRATION_TIME = 86400000L;
	
	private static SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
	
	public static  String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		String roles = populateAuthorities(authorities);
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email", auth.getName()) 
				.claim("authorities",roles)
				.signWith(key)
				.compact();
		return jwt;
	}
	
	public static  String getEmailFromToken(String token) {
		 if (token == null || !token.startsWith("Bearer ") || token.length() < 8) {
	            throw new IllegalArgumentException("Invalid token format");
	        }	
		token=token.substring(7);
		
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String email = String.valueOf(claims.get("email"));
        
        return String.valueOf(claims.get("email"));//email likha tha yaha
	}
	 

	private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auth=new HashSet<>();
		for(GrantedAuthority ga:authorities) {
			auth.add(ga.getAuthority());
		}
		return String.join( ",",auth);
	}

}
