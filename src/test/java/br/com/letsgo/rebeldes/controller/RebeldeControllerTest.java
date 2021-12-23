package br.com.letsgo.rebeldes.controller;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import br.com.letsgo.rebeldes.dto.RebeldeDTO;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.exception.SeeOtherException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.service.RebeldeService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class RebeldeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private HttpServletRequest servletRequest;

	@MockBean
	private RebeldeService rebeldeService;
	
	@Test
	public void getListRebeldesReturnOk() throws Exception {

		given(rebeldeService.getRebeldes()).willReturn(getListRebelde());
		mockMvc.perform(get("/v1/rebeldes")).andExpect(status().isOk());
	}
	
	@Test
	public void getListRebeldesReturnNotFound() throws Exception {

		given(rebeldeService.getRebeldes()).willThrow(NotFoundException.class);
		mockMvc.perform(get("/v1/rebeldes")).andExpect(status().isNotFound());
	}
	
	@Test
	public void getListRebeldesReturnInternalServerError() throws Exception {

		given(rebeldeService.getRebeldes()).willThrow(RebeldeException.class);
		mockMvc.perform(get("/v1/rebeldes")).andExpect(status().isInternalServerError());
	}
	
	
	@Test
	public void getRebeldesReturnOk() throws Exception {

		given(rebeldeService.getRebelde(9L)).willReturn(getListRebelde().get(0));
		mockMvc.perform(get("/v1/rebeldes/9")).andExpect(status().isOk());
	}
	
	@Test
	public void getRebeldesReturnNotFound() throws Exception {

		given(rebeldeService.getRebelde(1L)).willThrow(NotFoundException.class);
		mockMvc.perform(get("/v1/rebeldes/1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void getRebeldesReturnInternalServerError() throws Exception {

		given(rebeldeService.getRebelde(9L)).willThrow(RebeldeException.class);
		mockMvc.perform(get("/v1/rebeldes/9")).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void createRebeldeCreated() throws Exception, SeeOtherException {
		given(rebeldeService.salvarRebelde(getRebeldeDTO("Luke"), servletRequest)).willReturn(new URI("http://localhost:8080/v1/rebeldes/9"));
		mockMvc.perform(post("/v1/rebeldes").content(getRebeldeJson("Luke")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}
	
	
	@Test
	public void createRebeldeSeeOther() throws Exception, SeeOtherException {
		given(rebeldeService.salvarRebelde(Mockito.any(RebeldeDTO.class), Mockito.any(HttpServletRequest.class))).willThrow(SeeOtherException.class);
		mockMvc.perform(post("/v1/rebeldes").content(getRebeldeJson("LEIA")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isSeeOther());
	}
	
	@Test
	public void createRebeldeInternalError() throws SeeOtherException, Exception {
		given(rebeldeService.salvarRebelde(Mockito.any(RebeldeDTO.class), Mockito.any(HttpServletRequest.class))).willThrow(RebeldeException.class);
		mockMvc.perform(post("/v1/rebeldes").content(getRebeldeJson("HAN SOLO")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void getInventarioReturnOk() throws Exception {

		given(rebeldeService.getInventario(1L)).willReturn(new ArrayList<Inventario>());
		mockMvc.perform(get("/v1/rebeldes/1/inventario")).andExpect(status().isOk());
	}
	
	@Test
	public void getInventarioReturnNotFound() throws Exception {

		given(rebeldeService.getInventario(1L)).willThrow(NotFoundException.class);
		mockMvc.perform(get("/v1/rebeldes/1/inventario")).andExpect(status().isNotFound());
	}
	
	@Test
	public void getInventarioReturnInternalServerError() throws Exception {

		given(rebeldeService.getInventario(1L)).willThrow(RebeldeException.class);
		mockMvc.perform(get("/v1/rebeldes/1/inventario")).andExpect(status().isInternalServerError());
	}
	
	
	private List<Rebelde> getListRebelde(){
		List<Rebelde> list = new ArrayList<Rebelde>();
		var rebelde = new Rebelde();
		rebelde.setFielACausa(true);
		rebelde.setGenero("FEMININO");
		rebelde.setId(9L);
		rebelde.setLocalizacao(new Localizacao());
		rebelde.setNome("Sabine Wren");
		list.add(rebelde);
		return list;
	}
	
	private RebeldeDTO getRebeldeDTO(String nome) {
		var rebelde = new RebeldeDTO();
		rebelde.setGenero("FEMININO");
		rebelde.setId(9L);
		rebelde.setIdade(new BigInteger(30 + ""));
		
		rebelde.setNome(nome);
		
		var list = new ArrayList<Inventario>();
		var inve = new Inventario();
		var item = new Item();
		item.setId(3L);
		inve.setItem(item);
		inve.setQtd(new BigInteger(1+""));
		
		list.add(inve);
		rebelde.setInventario(list);
		
		var local = new Localizacao();
		local.setId(3L);
		rebelde.setLocalizacao(local);
		return rebelde;
	}
	

	private String getRebeldeJson(String nome){
		StringBuilder json = new StringBuilder("{");
		json.append("\"nome\": \""+ nome + "\",");
		json.append("\"idade\":30,");
		json.append("\"genero\": \"MASCULINO\",");
		json.append("\"localizacao\":3,");
		json.append("\"inventario\":[{");
		json.append("\"item\":3,\"qtd\":1}]}");
		return json.toString();
	}
	

}
