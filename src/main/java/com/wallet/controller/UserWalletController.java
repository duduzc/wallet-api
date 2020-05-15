package com.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.UserWalletDTO;
import com.wallet.entity.User;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.response.Response;
import com.wallet.service.UserWalletService;

@RestController
@RequestMapping("/user-wallet")
public class UserWalletController {
	
	@Autowired
	private UserWalletService userWalletService;
	
	@PostMapping
	public ResponseEntity<Response<UserWalletDTO>> create (@Valid @RequestBody UserWalletDTO userWalletDTO, BindingResult result) {
		
		Response<UserWalletDTO> response = new Response<UserWalletDTO>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(errors 
					-> response.getErrors().add(errors.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		UserWallet userWallet = userWalletService.save(this.convertDTOToEntity(userWalletDTO));
		
		response.setData(this.convertEntityToDTO(userWallet));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	private UserWallet convertDTOToEntity(UserWalletDTO userWalletDTO) {
		UserWallet userWallet = new UserWallet();
		User user = new User();
		user.setId(userWalletDTO.getUsers());
		
		Wallet wallet = new Wallet();
		wallet.setId(userWalletDTO.getWallet());
		
		userWallet.setId(userWalletDTO.getId());
		userWallet.setUsers(user);
		userWallet.setWallet(wallet);
		
		return userWallet;
	}
	
	private UserWalletDTO convertEntityToDTO(UserWallet userWallet) {
		UserWalletDTO userWalletDTO = new UserWalletDTO();
		userWalletDTO.setId(userWallet.getId());
		userWalletDTO.setUsers(userWallet.getUsers().getId());
		userWalletDTO.setWallet(userWallet.getWallet().getId());
		
		return userWalletDTO;	
	}
}