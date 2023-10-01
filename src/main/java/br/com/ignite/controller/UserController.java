package br.com.ignite.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ignite.dto.UserDTO;
import br.com.ignite.entity.Company;
import br.com.ignite.entity.User;
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
	public ResponseEntity<?> post(@RequestBody @Valid UserDTO userDTO) {
		try {
			User newUser = new User();
			newUser.setEnabled(true);
			newUser.setName(userDTO.getName());
			newUser.setEmail(userDTO.getEmail());
			newUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
			newUser.setDateCreation(new Date());
			newUser.setType(userDTO.getType());

			if (userDTO.getCompany() == null) {
				return new ResponseEntity<String>("Informe a empresa que o usuário pertence", HttpStatus.BAD_REQUEST);
			}
			
			Company company = new Company();
			company.setId(userDTO.getCompany().getId());
			newUser.setCompany(company);
			
			User userSaved = userService.saveAndFlush(newUser);
			UserDTO savedUser = map(userSaved);
			return new ResponseEntity<UserDTO>(savedUser, HttpStatus.CREATED);			
		} catch (Exception e) {
			System.out.println("########## " + e.getMessage() + " ##########");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> findAll() {
//		UsernamePasswordAuthenticationToken userLoggedIn = (UsernamePasswordAuthenticationToken) principal;
//		if (userLoggedIn.isAuthenticated()) {
//			List<User> users = userService.findAll();
//			List<UserDTO> usersDTO = map(users);
//			return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
//		}
//		return new ResponseEntity<>("Sem dados", HttpStatus.BAD_REQUEST);
		
		List<User> users = userService.findAll();
		
		if (users.isEmpty()) {
			return new ResponseEntity<>("Sem dados", HttpStatus.BAD_REQUEST);
		}
		List<UserDTO> usersDTO = map(users);
//		List<Company> companyDTO = companyService.findAll();
		return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
		
	}
	
	@PostMapping("/login")
	@Transactional
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			System.out.println("########## Usuário: " + user.getEmail() + " ##########");
			String userExists = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
			if (userExists != null) {				
				return new ResponseEntity<>(userExists, HttpStatus.OK);
			}
			return new ResponseEntity<>("Erro", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			System.out.println("########## " + e.getMessage() + " ##########");
			return new ResponseEntity<String>("Usuário/Senha inválido", HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/user/{email}")
	public ResponseEntity<?> findUser(@PathVariable String email) {
		try {
			User userLoggedIn = userService.findByEmail(email);
			UserDTO user = map(userLoggedIn);
			return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Usuário não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	
	private UserDTO map(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setEnabled(user.getEnabled());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setDateCreation(user.getDateCreation());
		userDTO.setType(user.getType());
		userDTO.setCompany(user.getCompany());
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
