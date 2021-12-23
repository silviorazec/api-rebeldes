package br.com.letsgo.rebeldes.controller;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import br.com.letsgo.rebeldes.dto.ConsolidadoDTO;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.service.RelatorioService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class RelatorioControllerTest {

	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	private RelatorioService relatorioService;
	

	
	@Test
	public void getConsolidadoOk() throws Exception {

		given(relatorioService.getRelatorioConsolidado()).willReturn(new ConsolidadoDTO());
		mockMvc.perform(get("/v1/relatorios/consolidado").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void getConsolidadoInternalServerError() throws Exception {

		given(relatorioService.getRelatorioConsolidado()).willThrow(RebeldeException.class);
		mockMvc.perform(get("/v1/relatorios/consolidado").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}

}
