package br.com.letsgo.rebeldes.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.letsgo.rebeldes.dto.RebeldeDTO;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.exception.SeeOtherException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.repositories.InventarioRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@SpringBootTest
public class RebeldeServiceTest {

	@Mock
	RebeldeRepository rebeldeRepository;
	
	@Mock
	InventarioRepository inventarioRepository;
	
	MockHttpServletRequest request = new MockHttpServletRequest();
	
	@InjectMocks
	RebeldeService rebeldeService;
	
	
	
	@Test
	public void salvarRebeldeSucesso() throws RebeldeException, URISyntaxException, SeeOtherException {
		
		
		request.setRequestURI("http://localhost:8080/v1/rebeldes");
		var rebelde = getRebeldeDTO("LEIA");
		var list = new ArrayList<Rebelde>();
		var listInventario = new ArrayList<Inventario>();
		
		var entityRebelde = rebelde.toRebelde();
		entityRebelde.setId(9L);
		
		Mockito.when(rebeldeRepository.findByNome(rebelde.getNome())).thenReturn(list);
		Mockito.when(rebeldeRepository.save(Mockito.any(Rebelde.class))).thenReturn(entityRebelde);
		
		Mockito.when(inventarioRepository.saveAll(rebelde.toListInventarioCompleto(rebelde.toRebelde()))).thenReturn(listInventario);
		
		assertThat(rebeldeService.salvarRebelde(rebelde,request)).isEqualTo(new URI("http://localhost:8080/v1/rebeldes/9"));
	
		 
	}
	
	@Test
	public void salvarRebeldeSeeOther() {
		var rebelde = getRebeldeDTO("LEIA");
		
		var list = new ArrayList<Rebelde>();
		list.add(rebelde.toRebelde());
		Mockito.when(rebeldeRepository.findByNome(rebelde.getNome())).thenReturn(list);
		
		Assertions.assertThrows(SeeOtherException.class, () -> rebeldeService.salvarRebelde(rebelde,request));
	
		 
	}
	
	@Test
	public void salvarRebeldeException() {
		var rebelde = getRebeldeDTO("LEIA");
		
		List<Rebelde> list =  null;
		Mockito.when(rebeldeRepository.findByNome(rebelde.getNome())).thenReturn(list);
		
		Assertions.assertThrows(RebeldeException.class, () -> rebeldeService.salvarRebelde(rebelde,request));
	
		 
	}
	
	
	@Test
	public void getRebeldesSucesso() throws NotFoundException, RebeldeException {
		var rebelde = getRebeldeDTO("LEIA");
		
		var list = new ArrayList<Rebelde>();
		list.add(rebelde.toRebelde());
		Mockito.when(rebeldeRepository.findByFielACausa(true)).thenReturn(list);		
		assertThat(rebeldeService.getRebeldes()).isEqualTo(list);

	}
	
	@Test
	public void getRebeldesNotFound() throws NotFoundException, RebeldeException {
		
		var list = new ArrayList<Rebelde>();
		Mockito.when(rebeldeRepository.findByFielACausa(true)).thenReturn(list);
		Assertions.assertThrows(NotFoundException.class, () -> rebeldeService.getRebeldes());
		 
	}
	
	@Test
	public void getRebeldesRebeldeException() throws NotFoundException, RebeldeException {
		
		Mockito.when(rebeldeRepository.findByFielACausa(true)).thenReturn(null);
		Assertions.assertThrows(RebeldeException.class, () -> rebeldeService.getRebeldes());
		 
	}
	
	
	
	@Test
	public void getRebeldeSucesso() throws NotFoundException, RebeldeException {
		var rebelde = getRebeldeDTO("LEIA");
		var rebeldeEntity = rebelde.toRebelde();
		rebeldeEntity.setId(9L);
		Optional<Rebelde> rebeldeOp = Optional.of(rebeldeEntity);
		Mockito.when(rebeldeRepository.findByIdAndFielACausa(9L,true)).thenReturn(rebeldeOp);
		
		assertThat(rebeldeService.getRebelde(9L)).isEqualTo(rebeldeEntity);
	
		 
	}
	
	@Test
	public void getRebeldeNotFound() throws NotFoundException, RebeldeException {
		
		Optional<Rebelde> rebeldeOp = Optional.empty();
		Mockito.when(rebeldeRepository.findByIdAndFielACausa(9L,true)).thenReturn(rebeldeOp);
		Assertions.assertThrows(NotFoundException.class, () -> rebeldeService.getRebelde(9L));
	
		 
	}
	
	@Test
	public void getRebeldeRebeldeException() throws NotFoundException, RebeldeException {
		
		Optional<Rebelde> rebeldeOp = null;
		Mockito.when(rebeldeRepository.findByIdAndFielACausa(9L,true)).thenReturn(rebeldeOp);
		Assertions.assertThrows(RebeldeException.class, () -> rebeldeService.getRebelde(9L));
		 
	}
	
	
	@Test
	public void getInventarioSucesso() throws NotFoundException, RebeldeException {

		var inventario = getInventario();
		List<Inventario> list = new ArrayList<Inventario>();
		list.add(inventario);
		Mockito.when(inventarioRepository.getByIdRebelde(9L)).thenReturn(list);
		
		assertThat(rebeldeService.getInventario(9L)).isEqualTo(list);
	
		 
	}
	
	@Test
	public void getInventarioNotFound() throws NotFoundException, RebeldeException {
		
	
		List<Inventario> list = new ArrayList<Inventario>();
		Mockito.when(inventarioRepository.getByIdRebelde(9L)).thenReturn(list);
		Assertions.assertThrows(NotFoundException.class, () -> rebeldeService.getInventario(9L));
	
		 
	}
	
	@Test
	public void getInventarioRebeldeException() throws NotFoundException, RebeldeException {
		
		
		Mockito.when(inventarioRepository.getByIdRebelde(9L)).thenReturn(null);
		Assertions.assertThrows(RebeldeException.class, () -> rebeldeService.getInventario(9L));
		 
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
	
	private Inventario getInventario() {
		var inventario = new Inventario();
		inventario.setId(1L);
		inventario.setItem(new Item());
		inventario.setQtd(BigInteger.TEN);
		
		var rebelde = getRebeldeDTO("LEIA").toRebelde();
		rebelde.setId(9L);
		inventario.setRebelde(rebelde);
		return inventario;
	}
}
