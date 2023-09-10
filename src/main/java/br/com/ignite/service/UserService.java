package br.com.ignite.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import br.com.ignite.entity.Company;
import br.com.ignite.entity.User;
import br.com.ignite.repository.CompanyRepository;
import br.com.ignite.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private TokenJWTService tokenService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	public User saveAndFlush(User user) throws Exception {
		Optional<User> userExists = userRepository.findByEmail(user.getEmail());
		if (userExists.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado - email: " + userExists.get().getEmail());
		} 
		return userRepository.saveAndFlush(user);
		// else if (userExists.isEmpty()) {
		// 	Optional<Company> companyExists = companyRepository.findById(user.getCompany().getId());
		// 	if (companyExists.isPresent()) {
		// 		return userRepository.saveAndFlush(user);
		// 	}
		// }
		// throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não cadastrado no sistema");
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public String findByEmailAndPassword(String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
		boolean isPassword = BCrypt.checkpw(password, user.get().getPassword());
		if (isPassword) {
			user.get().setLastLogin(new Date());
			String token = tokenService.generationToken(user.get());
			return token;
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário/Senha Invalido");
	}
	
	public User findByEmail(String email) throws IOException, HttpClientErrorException {
		try {
			Optional<User> user = userRepository.findByEmail(email);
			if (user.isPresent()) {
				return user.get();
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
