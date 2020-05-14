package com.wallet.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
public class UserDTO {
	
	private Long id;
	
	@Email(message="Email inválido!")
	private String email;
	
	@Length(min=3, max=50, message="O nome deve conter entre 3 e 50 caracteres!")
	private String nome;
	
	@NotNull
	@Length(min=6, message="A senha deve conter no minímo 6 caracteres!")
	private String password;

} 