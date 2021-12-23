package br.com.letsgo.rebeldes.controller;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Traicao;
import br.com.letsgo.rebeldes.service.TraicaoService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class TraicaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	private TraicaoService traicaoService;
	

	
	@Test
	public void informarTraicaoOk() throws  Exception {

		given(traicaoService.registrarTraicao(Mockito.any(Traicao.class))).willReturn(new Traicao());
		mockMvc.perform(post("/v1/traicoes").content(getTraicaoJson()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void informarTraicaoConflict() throws  Exception {
		given(traicaoService.registrarTraicao(Mockito.any(Traicao.class))).willThrow(ConflitctException.class);
		mockMvc.perform(post("/v1/traicoes").content(getTraicaoJson()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}
	
	@Test
	public void informarTraicaoInternalServeReRROR() throws  Exception {
		given(traicaoService.registrarTraicao(Mockito.any(Traicao.class))).willThrow(RebeldeException.class);
		mockMvc.perform(post("/v1/traicoes").content(getTraicaoJson()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
	

	private String getTraicaoJson() {
		var json = new StringBuilder();
		json.append("{");
		json.append("\"idDenunciante\":4,");
		json.append("\"idDenunciado\":2}");
		return json.toString();
	}

}
