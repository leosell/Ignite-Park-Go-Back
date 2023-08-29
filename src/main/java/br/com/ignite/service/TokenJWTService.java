package br.com.ignite.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.ignite.entity.User;

@Service
public class TokenJWTService {

	public String generationToken(User user) {
		try {
			var algoritmo = Algorithm.HMAC256("123456");
			return JWT.create()
					.withIssuer("API ToDoList")
					.withSubject(user.getUsername())
					.withExpiresAt(dataExpiracao())
					.sign(algoritmo);
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error ao gerar token ", e);
		}
	}
	
	public String verifyToken(String token) {
		try {
			token = token.replace("Bearer ", "");
			var algoritmo = Algorithm.HMAC256("123456");
			return JWT.require(algoritmo)
					.withIssuer("API ToDoList")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			return String.valueOf(new ResponseEntity<>("Token invalido/expirado", HttpStatus.BAD_REQUEST));
		}
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
