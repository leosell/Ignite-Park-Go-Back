package br.com.ignite.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ignite.dto.UserDTO;
import br.com.ignite.entity.User;
import br.com.ignite.enums.TypeUser;
import br.com.ignite.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping
	@Transactional
	@CrossOrigin
	public ResponseEntity<?> post(@RequestBody @Valid UserDTO userDTO) throws Exception, IOException {
		try {
			User newUser = new User();
			newUser.setEnabled(true);
			newUser.setName(userDTO.getName());
			newUser.setEmail(userDTO.getEmail());
			newUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
			newUser.setDateCreation(new Date());
			newUser.setType(TypeUser.USUARIO);
			
			User userSaved = userService.saveAndFlush(newUser);
			UserDTO savedUser = map(userSaved);
			return new ResponseEntity<UserDTO>(savedUser, HttpStatus.CREATED);			
		} catch (Exception e) {
			System.out.println("########## " + e.getMessage() + " ##########");
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ResponseEntity<String>("Usuário já existe", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> users = userService.findAll();
		List<UserDTO> usersDTO = map(users);
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	@Transactional
	public ResponseEntity<String> login(Principal principal, @RequestBody User user) {
		try {
			System.out.println("Usuário: " + user.getEmail() + " | Senha: " + user.getPassword());
			UsernamePasswordAuthenticationToken userLoggedIn = (UsernamePasswordAuthenticationToken) principal;
			if (userLoggedIn == null) {
				String userExists = userService.findByEmail(user.getEmail(), user.getPassword());
				return new ResponseEntity<String>(userExists, HttpStatus.OK);
			}
			System.out.println(userLoggedIn.getPrincipal());
			return new ResponseEntity<> (userLoggedIn.getPrincipal().toString(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			System.out.println("########## " + e.getMessage() + " ##########");
			return new ResponseEntity<String>("Usuário/Senha inválido", HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/validation")
	public ResponseEntity<?> validationLogin(Principal principal) {
		UsernamePasswordAuthenticationToken userLoggedIn = (UsernamePasswordAuthenticationToken) principal;
		return new ResponseEntity<>(userLoggedIn.getPrincipal(), HttpStatus.OK);
	}
	
	private UserDTO map(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setEnabled(user.getEnabled());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setDateCreation(user.getDateCreation());
		userDTO.setType(user.getType());
		return userDTO;
	}
	
	private List<UserDTO> map(List<User> users) {
		if (users == null) {
			return Collections.emptyList();
		}
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User user : users) {
			usersDTO.add(map(user));
		}
		return usersDTO;
	}
}
