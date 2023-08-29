package br.com.ignite.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.ignite.entity.User;
import br.com.ignite.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenJWTService tokenService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	public User saveAndFlush(User user) throws Exception {
		Optional<User> userExists = userRepository.findByEmail(user.getEmail());
		if (!userExists.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado - email: " + userExists.get().getEmail());
		}
		return userRepository.saveAndFlush(user);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public String findByEmail(String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
		boolean isPassword = BCrypt.checkpw(password, user.get().getPassword());
		if (isPassword) {
			user.get().setLastLogin(new Date());
			return tokenService.generationToken(user.get());
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário/Senha Invalido");
	}
	
	public String findByEmailAndPassword(String email, String password) throws AuthenticationException {
		try {
			Optional<User> user = userRepository.findByEmailAndPassword(email, password);
			System.out.println(user.get().getPassword());
			boolean passwordHas = new BCryptPasswordEncoder().upgradeEncoding(user.get().getPassword());
			System.out.println(passwordHas);
			if (user.isPresent()) {
				
				
				user.get().setLastLogin(new Date());
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
