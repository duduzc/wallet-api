package com.wallet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.repository.UserRepository;
import com.wallet.response.Response;
import com.wallet.service.UserService;
import com.wallet.util.Bcrypt;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
		
		Response<UserDTO> response = new Response<UserDTO>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(errors 
					-> response.getErrors().add(errors.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User user = userService.save(this.convertDTOToEntity(userDTO));
		
		response.setData(this.convertEntityToDTO(user));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getById() { 
		List<User> entity = userRepository.findAll();
		return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
	}
	
	private User convertDTOToEntity(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getNome());
		user.setPassword(Bcrypt.getHash(userDTO.getPassword()));
		
		return user;
	}
	
	private UserDTO convertEntityToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setNome(user.getName());
		
		return userDTO;	
	}
}
