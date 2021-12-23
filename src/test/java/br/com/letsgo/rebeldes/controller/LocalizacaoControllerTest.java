package br.com.letsgo.rebeldes.controller;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.service.LocalizacaoService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class LocalizacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	private LocalizacaoService localizacaoService;
	
	@Test
	public void getLocalizacaoReturnOk() throws Exception {

		given(localizacaoService.getLocalizacaByIdRebelde(1L)).willReturn(new Localizacao());
		mockMvc.perform(get("/v1/rebeldes/1/localizacao")).andExpect(status().isOk());
	}

	
	@Test
	public void getListRebeldesReturnNotFound() throws Exception {

		given(localizacaoService.getLocalizacaByIdRebelde(1L)).willThrow(NotFoundException.class);
		mockMvc.perform(get("/v1/rebeldes/1/localizacao")).andExpect(status().isNotFound());
	}
	
	@Test
	public void getListRebeldesReturnInternalServerError() throws Exception {

		given(localizacaoService.getLocalizacaByIdRebelde(1L)).willThrow(RebeldeException.class);
		mockMvc.perform(get("/v1/rebeldes/1/localizacao")).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void mudarLocalizacaoOk() throws  Exception {
		var localizacao = new Localizacao();
		localizacao.setId(1L);
		given(localizacaoService.atualizarLocalizacao(localizacao, 2L)).willReturn(localizacao);
		mockMvc.perform(put("/v1/rebeldes/2/localizacao").content("{\"id\":1}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	
	@Test
	public void mudarLocalizacaoConflict() throws  Exception {

		given(localizacaoService.atualizarLocalizacao(Mockito.any(Localizacao.class), Mockito.anyLong())).willThrow(ConflitctException.class);
		mockMvc.perform(put("/v1/rebeldes/2/localizacao").content("{\"id\":1}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}
	
	@Test
	public void mudarLocalizacaoNotFound() throws  Exception {
		given(localizacaoService.atualizarLocalizacao(Mockito.any(Localizacao.class), Mockito.anyLong())).willThrow(NotFoundException.class);
		mockMvc.perform(put("/v1/rebeldes/2/localizacao").content("{\"id\":1}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	@Test
	public void mudarLocalizacaoInternalServeReRROR() throws  Exception {
		given(localizacaoService.atualizarLocalizacao(Mockito.any(Localizacao.class), Mockito.anyLong())).willThrow(RebeldeException.class);
		mockMvc.perform(put("/v1/rebeldes/2/localizacao").content("{\"id\":1}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
	

	

}
