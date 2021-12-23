package br.com.letsgo.rebeldes.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.letsgo.rebeldes.dto.RebeldeDTO;
import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.repositories.LocalizacaoRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@SpringBootTest
public class LocalizacaoServiceTest {

	
	@Mock
	private LocalizacaoRepository localizacaoRepository;
	
	@Mock
	private RebeldeRepository rebeldeRepository;
	
	@InjectMocks
	LocalizacaoService localizacaoService;
	
	@Test
	public void getLocalizacaByIdRebeldeSucesso() throws RebeldeException, NotFoundException {

		var local = getLocalizacao();
		Mockito.when(localizacaoRepository.getLocalizacaoByIdRebelde(9L)).thenReturn(local);
	
		assertThat(localizacaoService.getLocalizacaByIdRebelde(9L)).isEqualTo(local);
	
		 
	}
	
	@Test
	public void getLocaNotdfound() {
		Mockito.when(localizacaoRepository.getLocalizacaoByIdRebelde(9L)).thenReturn(null);
	
		Assertions.assertThrows(NotFoundException.class, () -> localizacaoService.getLocalizacaByIdRebelde(9L));
		 
	}
	
	@Test
	public void getLocalRbeldeException() {
		Mockito.when(localizacaoRepository.getLocalizacaoByIdRebelde(9L)).thenThrow(NullPointerException.class);
	
		Assertions.assertThrows(RebeldeException.class, () -> localizacaoService.getLocalizacaByIdRebelde(9L));
		 
	}
	
	
	
	
	@Test
	public void atualizarLocalizacaoSucesso() throws RebeldeException, NotFoundException, ConflitctException {

		var local = getLocalizacao();
		var rebelde = Optional.of( getRebeldeDTO("LEIA").toRebelde());
		var localOp = Optional.of( local);;
				
		Mockito.when( rebeldeRepository.findById(9L)).thenReturn(rebelde);
		Mockito.when( localizacaoRepository.findById(local.getId())).thenReturn(localOp);
		Mockito.when( rebeldeRepository.save(rebelde.get())).thenReturn(rebelde.get());
		
	
		assertThat(localizacaoService.atualizarLocalizacao(local, 9L)).isEqualTo(local);
	
		 
	}
	
	@Test
	public void atualizarLocalizacaoSucessoNotFound() {
		
		var local = getLocalizacao();
		Optional<Rebelde> rebeldeop = Optional.empty();
		Mockito.when( rebeldeRepository.findById(9L)).thenReturn(rebeldeop);
	
		Assertions.assertThrows(NotFoundException.class, () -> localizacaoService.atualizarLocalizacao(local, 9L));
		 
	}
	
	@Test
	public void atualizarLocalizacaoSucessoRebeldeException() {
		
		var local = getLocalizacao();
		Mockito.when( rebeldeRepository.findById(9L)).thenThrow(NullPointerException.class);
	
		Assertions.assertThrows(RebeldeException.class, () -> localizacaoService.atualizarLocalizacao(local,9L));
		 
	}
	
	@Test
	public void atualizarLocalizacaoSucessoConflicException() {
		
		var local = getLocalizacao();
		var rebelde = Optional.of( getRebeldeDTO("LEIA").toRebelde());
		Optional<Localizacao> localOp = Optional.empty();
				
		Mockito.when( rebeldeRepository.findById(9L)).thenReturn(rebelde);
		Mockito.when( localizacaoRepository.findById(local.getId())).thenReturn(localOp);
	
		Assertions.assertThrows(ConflitctException.class, () -> localizacaoService.atualizarLocalizacao(local,9L));
		 
	}
	
	
	private Localizacao getLocalizacao() {
		var localizacao = new Localizacao();
		localizacao.setBase("Grande Templo Massassi");
		localizacao.setGalaxia("Galáxia de Órion");
		localizacao.setId(1L);
		localizacao.setLatitude("-106.8005");
		localizacao.setLongitude("28.3861");
		return localizacao;
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
	
	
}
