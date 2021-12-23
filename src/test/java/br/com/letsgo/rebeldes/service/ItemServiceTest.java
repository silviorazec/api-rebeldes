package br.com.letsgo.rebeldes.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.repositories.ItemRepository;

@SpringBootTest
public class ItemServiceTest {

	
	@Mock
	ItemRepository itemRepository;
	
	MockHttpServletRequest request = new MockHttpServletRequest();
	
	@InjectMocks
	ItemService itemService;
	
	
	
	@Test
	public void getItensSucesso() throws RebeldeException {
		
		var item = new Item();
		item.setId(1L);
		item.setNome("ARMA");
		item.setPontos(BigInteger.TWO);
		
		var list = new ArrayList<Item>();
		list.add(item);
		Mockito.when(itemRepository.findAll()).thenReturn(list);
	
		assertThat(itemService.getItens()).isEqualTo(list);
	
		 
	}
	
	@Test
	public void salvarRebeldeSeeOther() {
		Mockito.when(itemRepository.findAll()).thenThrow(NullPointerException.class);
		Assertions.assertThrows(RebeldeException.class, () -> itemService.getItens());
		 
	}
	
	
}
