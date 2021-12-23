package br.com.letsgo.rebeldes.controller;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.service.ItemService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class ItemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	private ItemService itemService;
	

	
	@Test
	public void getItensOk() throws Exception {

		given(itemService.getItens()).willReturn(new ArrayList<Item>());
		mockMvc.perform(get("/v1/itens")).andExpect(status().isOk());
	}
	
	@Test
	public void getItensOkInternalServerError() throws Exception {

		given(itemService.getItens()).willThrow(RebeldeException.class);
		mockMvc.perform(get("/v1/itens")).andExpect(status().isInternalServerError());
	}

}
