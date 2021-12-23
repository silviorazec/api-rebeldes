package br.com.letsgo.rebeldes.controller;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import br.com.letsgo.rebeldes.dto.NegociacaoDTO;
import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.service.ItemService;
import br.com.letsgo.rebeldes.service.NegociacaoService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class NegociacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	private NegociacaoService negociacaoService;
	

	
	@Test
	public void realizarNeogiacaoOk() throws Exception {

		doNothing().when(negociacaoService).efetuar(Mockito.any(NegociacaoDTO.class));
		mockMvc.perform(put("/v1/negociacoes").content(getNegociacaoJson()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}
	
	@Test
	public void realizarNeogiacaoConflict() throws Exception {

		doThrow(ConflitctException.class).when(negociacaoService).efetuar(Mockito.any(NegociacaoDTO.class));
		mockMvc.perform(put("/v1/negociacoes").content(getNegociacaoJson()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}
	
	
	@Test
	public void realizarNegociacaoInternalServerError() throws Exception {

		doThrow(RebeldeException.class).when(negociacaoService).efetuar(Mockito.any(NegociacaoDTO.class));
		mockMvc.perform(put("/v1/negociacoes").content(getNegociacaoJson()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
	
	
	private String getNegociacaoJson() {
		return  "{" +
			 "\"idRebeldeVendedor\":1," +
			 " \"itensVendedor\":[{" +
			 "	\"idItem\":2," +
			 "	\"qtd\":1" +
			 " }]," +
			 "\"idRebeldeComprador\":10," +
			 " \"itensComprador\":[{" +
			 "	\"idItem\":3," +
			 "	\"qtd\":1" +
			 " },{" +
			 "	\"idItem\":4," +
			 "	\"qtd\":1" +
			 " }]" +
			 "}";
	}

}
