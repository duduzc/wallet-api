package com.wallet.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.enums.TypeEnum;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class WalletItemRepositoryTest {
	
	private static final Date DATE = new Date();
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(60);
	private Long savedWalletItemId = null;
	private Long savedWalletId = null;
	
	@Autowired
	WalletItemRepository walletItemRepository;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Before
	public void setUp() {	
		Wallet wallet = new Wallet();
		wallet.setName("Carteira teste");
		wallet.setValue(BigDecimal.valueOf(1000));
		walletRepository.save(wallet);
		
		WalletItem walletItem = new WalletItem(null, wallet, DATE, TYPE, DESCRIPTION, VALUE);
		walletItemRepository.save(walletItem);
		
		savedWalletItemId = walletItem.getId();
		savedWalletId = wallet.getId();
	}
	
	@After
	public void tearDown() {
		walletItemRepository.deleteAll();
		walletRepository.deleteAll();
	}
	
	@Test
	public void testSave() {	
		Wallet wallet = new Wallet();
		wallet.setName("Carteira teste");
		wallet.setValue(BigDecimal.valueOf(1000));
		walletRepository.save(wallet);
		
		WalletItem walletItem = new WalletItem(1L, wallet, DATE, TYPE, DESCRIPTION, VALUE);
		
		WalletItem response = walletItemRepository.save(walletItem);
		
		assertNotNull(response);
		assertEquals(response.getDescription(), DESCRIPTION);
		assertEquals(response.getType(), TYPE);
		assertEquals(response.getValue(), VALUE);
		assertEquals(response.getWallet().getId(), wallet.getId());
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveInvalidWalletItem() {
		WalletItem walletItem = new WalletItem(null, null, DATE, null, DESCRIPTION, null);
		walletItemRepository.save(walletItem);
	}
	
	@Test
	public void testUpdate() {
		Optional<WalletItem> walletItem = walletItemRepository.findById(savedWalletItemId);
		
		String description = "Descrição alterada";
		
		WalletItem changed = walletItem.get();
		changed.setDescription(description);
		
		walletItemRepository.save(changed);
		
		Optional<WalletItem> newWalletItem = walletItemRepository.findById(savedWalletItemId);
		
		assertEquals(description, newWalletItem.get().getDescription());
	}
	
	@Test
	public void deleteWalletItem() {
		Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
		WalletItem walletItem = new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, VALUE);
		
		walletItemRepository.save(walletItem);
		
		walletItemRepository.deleteById(walletItem.getId());
		
		Optional<WalletItem> response = walletItemRepository.findById(walletItem.getId());
		
		assertFalse(response.isPresent());
	}

}