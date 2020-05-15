package com.wallet.dto;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class WalletItemDTO {
	
	private Long id;
	
	@NotNull(message = "Insira o id da carteira!")
	private Long wallet;
	
	@NotNull(message = "Data é obrigatória!")
	private Date date;
	
	@NotNull(message = "Informe um tipo!")
	private String type;
	
	@NotNull(message = "Informe uma descrição!")
	@Length(min = 5, message = "A descrição deve ter no mínimo 5 caracteres!")
	private String description;
	
	@NotNull(message = "Informe um tipo!")
	private BigDecimal value;
	
}