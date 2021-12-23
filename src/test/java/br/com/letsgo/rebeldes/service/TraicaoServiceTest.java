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
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.modelo.Traicao;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;
import br.com.letsgo.rebeldes.repositories.TraicaoRepository;

@SpringBootTest
public class TraicaoServiceTest {

	@Mock
	private TraicaoRepository traicaoRepository;
	@Mock
	private RebeldeRepository rebeldeRepository;
	
	@InjectMocks
	TraicaoService traicaoService;
	
	@Test
	public void registrarTraicaoSucessoTraicaoConfirmada() throws RebeldeException, ConflitctException {
		
		var envolvidos = new ArrayList<Long>();
		envolvidos.add(1L);
		envolvidos.add(9L);

		var listRebelde = new ArrayList<Rebelde>();
		listRebelde.add(new Rebelde());
		listRebelde.add(new Rebelde());
		
		var traicao = getTraicao();
		
		var listTraicao = new ArrayList<Traicao>();
		listTraicao.add(new Traicao());
		listTraicao.add(new Traicao());
		listTraicao.add(new Traicao());
		
		Optional<Traicao> traicaoOP = Optional.empty();
		
		var rebelde = Optional.of(getRebeldeDTO("LEIA").toRebelde());
		
		Mockito.when(rebeldeRepository.findAllById(Mockito.anyIterable())).thenReturn(listRebelde);
		Mockito.when(traicaoRepository.findByIdDenunciadoAndIdDenunciante(Mockito.anyLong(), Mockito.anyLong())).thenReturn(traicaoOP);
		Mockito.when(traicaoRepository.save(Mockito.any(Traicao.class))).thenReturn(traicao);
		Mockito.when(traicaoRepository.findByIdDenunciado(Mockito.anyLong())).thenReturn(listTraicao);
		Mockito.when(rebeldeRepository.findById(Mockito.anyLong())).thenReturn(rebelde);
		Mockito.when(rebeldeRepository.save(rebelde.get())).thenReturn(rebelde.get());
		
		
		
	
		assertThat(traicaoService.registrarTraicao(traicao)).isEqualTo(traicao);
	
		 
	}

	
	@Test
	public void registrarTraicaoSucessoTraicaoNaoConfirmada() throws RebeldeException, ConflitctException {
		
		var envolvidos = new ArrayList<Long>();
		envolvidos.add(1L);
		envolvidos.add(9L);

		var listRebelde = new ArrayList<Rebelde>();
		listRebelde.add(new Rebelde());
		listRebelde.add(new Rebelde());
		
		var traicao = getTraicao();
		
		var listTraicao = new ArrayList<Traicao>();
		listTraicao.add(new Traicao());
		
		Optional<Traicao> traicaoOP = Optional.empty();
		
		var rebelde = Optional.of(getRebeldeDTO("LEIA").toRebelde());
		
		Mockito.when(rebeldeRepository.findAllById(Mockito.anyIterable())).thenReturn(listRebelde);
		Mockito.when(traicaoRepository.findByIdDenunciadoAndIdDenunciante(Mockito.anyLong(), Mockito.anyLong())).thenReturn(traicaoOP);
		Mockito.when(traicaoRepository.save(Mockito.any(Traicao.class))).thenReturn(traicao);
		Mockito.when(traicaoRepository.findByIdDenunciado(Mockito.anyLong())).thenReturn(listTraicao);
		Mockito.when(rebeldeRepository.findById(Mockito.anyLong())).thenReturn(rebelde);
		Mockito.when(rebeldeRepository.save(rebelde.get())).thenReturn(rebelde.get());
		
		assertThat(traicaoService.registrarTraicao(traicao)).isEqualTo(traicao);
	
		 
	}
	
	@Test
	public void registrarTraicaoSucessoTraicaoConflic() throws RebeldeException, ConflitctException {
		
		var envolvidos = new ArrayList<Long>();
		envolvidos.add(1L);
		envolvidos.add(9L);

		var listRebelde = new ArrayList<Rebelde>();
		listRebelde.add(new Rebelde());
		
		var traicao = getTraicao();
		
		var listTraicao = new ArrayList<Traicao>();
		listTraicao.add(new Traicao());
		
		Optional<Traicao> traicaoOP = Optional.empty();
		
		var rebelde = Optional.of(getRebeldeDTO("LEIA").toRebelde());
		
		Mockito.when(rebeldeRepository.findAllById(Mockito.anyIterable())).thenReturn(listRebelde);
		Mockito.when(traicaoRepository.findByIdDenunciadoAndIdDenunciante(Mockito.anyLong(), Mockito.anyLong())).thenReturn(traicaoOP);
		Mockito.when(traicaoRepository.save(Mockito.any(Traicao.class))).thenReturn(traicao);
		Mockito.when(traicaoRepository.findByIdDenunciado(Mockito.anyLong())).thenReturn(listTraicao);
		Mockito.when(rebeldeRepository.findById(Mockito.anyLong())).thenReturn(rebelde);
		Mockito.when(rebeldeRepository.save(rebelde.get())).thenReturn(rebelde.get());
		
		Assertions.assertThrows(ConflitctException.class, () -> traicaoService.registrarTraicao(getTraicao()));
	
		 
	}
	
	@Test
	public void registrarTraicaoSucessoTraicaoRebeldeException() throws RebeldeException, ConflitctException {
		
		var envolvidos = new ArrayList<Long>();
		envolvidos.add(1L);

		var listRebelde = new ArrayList<Rebelde>();
		listRebelde.add(new Rebelde());
		listRebelde.add(new Rebelde());
		
		Mockito.when(rebeldeRepository.findAllById(Mockito.anyIterable())).thenThrow(NullPointerException.class);
		Assertions.assertThrows(RebeldeException.class, () -> traicaoService.registrarTraicao(getTraicao()));
	
		 
	}
	
	
	private Traicao getTraicao() {
		var traicao = new Traicao();
		traicao.setId(1L);
		traicao.setIdDenunciado(9L);
		traicao.setIdDenunciado(1L);
		return traicao;
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
