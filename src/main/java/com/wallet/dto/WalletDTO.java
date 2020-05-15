package com.wallet.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class WalletDTO {
	
	private Long id;
	
	@Length(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
	@NotNull(message = "Nome deve ser informado!")
	private String name;
	
	@NotNull(message = "Valor deve ser informado!")
	private BigDecimal value;

}