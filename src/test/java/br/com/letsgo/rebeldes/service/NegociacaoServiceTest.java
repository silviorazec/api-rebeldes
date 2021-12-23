package br.com.letsgo.rebeldes.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.letsgo.rebeldes.dto.ItensDTO;
import br.com.letsgo.rebeldes.dto.NegociacaoDTO;
import br.com.letsgo.rebeldes.dto.RebeldeDTO;
import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.repositories.InventarioRepository;
import br.com.letsgo.rebeldes.repositories.ItemRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@SpringBootTest
public class NegociacaoServiceTest {

	@Mock
	ItemRepository itemRepository;
	@Mock
	InventarioRepository inventarioRepository;
	@Mock
	RebeldeRepository rebeldeRepository;
	
	@InjectMocks
	NegociacaoService negociacaoService;
	
	@Test
	public void efetuarNegociacaoSucesso() throws RebeldeException, ConflitctException {
		
		var comprador = getRebelde("LEIA", 1L);
		var vendedor = getRebelde("PADME", 2L);
		
		var listItemComprador = new ArrayList<Item>();
		listItemComprador.add(getItem(1L, 1, "AGUA"));
		listItemComprador.add(getItem(2L, 1, "ARMA"));
		
		var listItemVendedor= new ArrayList<Item>();
		listItemVendedor.add(getItem(3l, 1, "MUNICAO"));
		listItemVendedor.add(getItem(4l, 1, "COMIDA"));
		
		
		var listInventarioComprador = new ArrayList<Inventario>();
		listInventarioComprador.add(getInventario(1L, listItemComprador.get(0), comprador, 4));
		listInventarioComprador.add(getInventario(2L, listItemComprador.get(1), comprador, 4));
		
		var listInventarioVendedor = new ArrayList<Inventario>();
		listInventarioVendedor.add(getInventario(3L, listItemVendedor.get(0), comprador, 4));
		listInventarioVendedor.add(getInventario(4L, listItemVendedor.get(1), comprador, 4));
		
		var listNegociacaoComprador = new ArrayList<ItensDTO>();
		listNegociacaoComprador.add(getItenmDTO(1L, 2));
		listNegociacaoComprador.add(getItenmDTO(2L, 2));
		
		var listNegociacaoVendedor= new ArrayList<ItensDTO>();
		listNegociacaoVendedor.add(getItenmDTO(3L, 2));
		listNegociacaoVendedor.add(getItenmDTO(4L, 2));
		
		var negociacao = getNegociacao(comprador.getId(), vendedor.getId(), listNegociacaoComprador, listNegociacaoVendedor);
		
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(comprador.getId(), listInventarioComprador.get(0).getId())).thenReturn(Optional.of(listInventarioComprador.get(0)));
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(comprador.getId(), listInventarioComprador.get(1).getId())).thenReturn(Optional.of(listInventarioComprador.get(1)));
		
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(vendedor.getId(), listInventarioVendedor.get(0).getId())).thenReturn(Optional.of(listInventarioVendedor.get(0)));
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(vendedor.getId(), listInventarioVendedor.get(1).getId())).thenReturn(Optional.of(listInventarioVendedor.get(1)));
		
		
		Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(listItemComprador.get(0)));
		Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.of(listItemComprador.get(1)));
		
		Mockito.when(itemRepository.findById(3L)).thenReturn(Optional.of(listItemVendedor.get(0)));
		Mockito.when(itemRepository.findById(4L)).thenReturn(Optional.of(listItemVendedor.get(1)));
		
		
		Mockito.when(inventarioRepository.save(Mockito.any(Inventario.class))).thenReturn(new Inventario());
		
		Mockito.when(rebeldeRepository.findById(comprador.getId())).thenReturn(Optional.of(comprador));
		Mockito.when(rebeldeRepository.findById(vendedor.getId())).thenReturn(Optional.of(vendedor));
		

		Mockito.when(itemRepository.findById(listItemComprador.get(0).getId())).thenReturn(Optional.of(listItemComprador.get(0)));
		Mockito.when(itemRepository.findById(listItemComprador.get(1).getId())).thenReturn(Optional.of(listItemComprador.get(1)));
		Mockito.when(itemRepository.findById(listItemVendedor.get(0).getId())).thenReturn(Optional.of(listItemVendedor.get(0)));
		Mockito.when(itemRepository.findById(listItemVendedor.get(0).getId())).thenReturn(Optional.of(listItemVendedor.get(1)));

		Assertions.assertDoesNotThrow(() -> negociacaoService.efetuar(negociacao));
	
		 
	}
	
	@Test
	public void efetuarNegociacaoConflic() throws RebeldeException, ConflitctException {
		
		var comprador = getRebelde("LEIA", 1L);
		var vendedor = getRebelde("PADME", 2L);
		
		var listItemComprador = new ArrayList<Item>();
		listItemComprador.add(getItem(1L, 1, "AGUA"));
		listItemComprador.add(getItem(2L, 1, "ARMA"));
		
		var listItemVendedor= new ArrayList<Item>();
		listItemVendedor.add(getItem(3l, 1, "MUNICAO"));
		listItemVendedor.add(getItem(4l, 1, "COMIDA"));
		
		
		var listInventarioComprador = new ArrayList<Inventario>();
		listInventarioComprador.add(getInventario(1L, listItemComprador.get(0), comprador, 4));
		listInventarioComprador.add(getInventario(2L, listItemComprador.get(1), comprador, 4));
		
		var listInventarioVendedor = new ArrayList<Inventario>();
		listInventarioVendedor.add(getInventario(3L, listItemVendedor.get(0), comprador, 4));
		listInventarioVendedor.add(getInventario(4L, listItemVendedor.get(1), comprador, 4));
		
		var listNegociacaoComprador = new ArrayList<ItensDTO>();
		listNegociacaoComprador.add(getItenmDTO(1L, 2));
		listNegociacaoComprador.add(getItenmDTO(2L, 2));
		
		var listNegociacaoVendedor= new ArrayList<ItensDTO>();
		listNegociacaoVendedor.add(getItenmDTO(3L, 2));
		listNegociacaoVendedor.add(getItenmDTO(4L, 2));
		
		var negociacao = getNegociacao(comprador.getId(), vendedor.getId(), listNegociacaoComprador, listNegociacaoVendedor);
		
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(comprador.getId(), 1L)).thenReturn(Optional.of(listInventarioComprador.get(0)));
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(comprador.getId(), 2L)).thenReturn(Optional.of(listInventarioComprador.get(1)));
		
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(vendedor.getId(), 3L)).thenReturn(Optional.of(listInventarioVendedor.get(0)));
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(vendedor.getId(), 4L)).thenReturn(Optional.empty());
		
		
		Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(listItemComprador.get(0)));
		Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.of(listItemComprador.get(1)));
		
		Mockito.when(itemRepository.findById(3L)).thenReturn(Optional.of(listItemVendedor.get(0)));
		Mockito.when(itemRepository.findById(4L)).thenReturn(Optional.of(listItemVendedor.get(1)));
		
		
		Mockito.when(inventarioRepository.save(Mockito.any(Inventario.class))).thenReturn(new Inventario());
		
		Mockito.when(rebeldeRepository.findById(comprador.getId())).thenReturn(Optional.of(comprador));
		Mockito.when(rebeldeRepository.findById(vendedor.getId())).thenReturn(Optional.of(vendedor));
		

		Mockito.when(itemRepository.findById(listItemComprador.get(0).getId())).thenReturn(Optional.of(listItemComprador.get(0)));
		Mockito.when(itemRepository.findById(listItemComprador.get(1).getId())).thenReturn(Optional.of(listItemComprador.get(1)));
		Mockito.when(itemRepository.findById(listItemVendedor.get(0).getId())).thenReturn(Optional.of(listItemVendedor.get(0)));
		Mockito.when(itemRepository.findById(listItemVendedor.get(0).getId())).thenReturn(Optional.of(listItemVendedor.get(1)));

		Assertions.assertThrows(ConflitctException.class, () -> negociacaoService.efetuar(negociacao));
	
		 
	}
	
	@Test
	public void efetuarNegociacaoRebeldeException() throws RebeldeException, ConflitctException {
		
		var comprador = getRebelde("LEIA", 1L);
		var vendedor = getRebelde("PADME", 2L);
		
		var listItemComprador = new ArrayList<Item>();
		listItemComprador.add(getItem(1L, 1, "AGUA"));
		listItemComprador.add(getItem(2L, 1, "ARMA"));
		
		var listItemVendedor= new ArrayList<Item>();
		listItemVendedor.add(getItem(3l, 1, "MUNICAO"));
		listItemVendedor.add(getItem(4l, 1, "COMIDA"));
		
		
		var listInventarioComprador = new ArrayList<Inventario>();
		listInventarioComprador.add(getInventario(1L, listItemComprador.get(0), comprador, 4));
		listInventarioComprador.add(getInventario(2L, listItemComprador.get(1), comprador, 4));
		
		var listInventarioVendedor = new ArrayList<Inventario>();
		listInventarioVendedor.add(getInventario(3L, listItemVendedor.get(0), comprador, 4));
		listInventarioVendedor.add(getInventario(4L, listItemVendedor.get(1), comprador, 4));
		
		var listNegociacaoComprador = new ArrayList<ItensDTO>();
		listNegociacaoComprador.add(getItenmDTO(1L, 2));
		listNegociacaoComprador.add(getItenmDTO(2L, 2));
		
		var listNegociacaoVendedor= new ArrayList<ItensDTO>();
		listNegociacaoVendedor.add(getItenmDTO(3L, 2));
		listNegociacaoVendedor.add(getItenmDTO(4L, 2));
		
		var negociacao = getNegociacao(comprador.getId(), vendedor.getId(), listNegociacaoComprador, listNegociacaoVendedor);
		
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(comprador.getId(), 1L)).thenReturn(Optional.of(listInventarioComprador.get(0)));
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(comprador.getId(), 2L)).thenReturn(Optional.of(listInventarioComprador.get(1)));
		
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(vendedor.getId(), 3L)).thenReturn(Optional.of(listInventarioVendedor.get(0)));
		Mockito.when(inventarioRepository.getByIdRebeldeAndIdItem(vendedor.getId(), 4L)).thenThrow(NullPointerException.class);
		
		
		Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(listItemComprador.get(0)));
		Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.of(listItemComprador.get(1)));
		
		Mockito.when(itemRepository.findById(3L)).thenReturn(Optional.of(listItemVendedor.get(0)));
		Mockito.when(itemRepository.findById(4L)).thenReturn(Optional.of(listItemVendedor.get(1)));
		
		
		Mockito.when(inventarioRepository.save(Mockito.any(Inventario.class))).thenReturn(new Inventario());
		
		Mockito.when(rebeldeRepository.findById(comprador.getId())).thenReturn(Optional.of(comprador));
		Mockito.when(rebeldeRepository.findById(vendedor.getId())).thenReturn(Optional.of(vendedor));
		

		Mockito.when(itemRepository.findById(listItemComprador.get(0).getId())).thenReturn(Optional.of(listItemComprador.get(0)));
		Mockito.when(itemRepository.findById(listItemComprador.get(1).getId())).thenReturn(Optional.of(listItemComprador.get(1)));
		Mockito.when(itemRepository.findById(listItemVendedor.get(0).getId())).thenReturn(Optional.of(listItemVendedor.get(0)));
		Mockito.when(itemRepository.findById(listItemVendedor.get(0).getId())).thenReturn(Optional.of(listItemVendedor.get(1)));

		Assertions.assertThrows(RebeldeException.class, () -> negociacaoService.efetuar(negociacao));
	
		 
	}
	
	
	

	private ItensDTO getItenmDTO(Long id, int qtd) {
		var item = new ItensDTO();
		item.setIdItem(id);
		item.setQtd(new BigInteger(qtd + "") );
		
		return item;
	}
	
	private NegociacaoDTO getNegociacao(Long idRebelde1, Long idRebelde2, List<ItensDTO> itensRebelde1, List<ItensDTO> itensRebelde2) {
		var negociacao = new NegociacaoDTO();
		
		negociacao.setIdRebeldeComprador(idRebelde1);
		negociacao.setIdRebeldeVendedor(idRebelde2);
		negociacao.setItensComprador(itensRebelde1);
		negociacao.setItensVendedor(itensRebelde2);
		return negociacao;
	}
	
	
	private Inventario getInventario(Long id, Item item, Rebelde rebelde, int qtd){
		
		var inventario = new Inventario();
		inventario.setId(id);
		inventario.setItem(item);
		inventario.setQtd(new  BigInteger(qtd + ""));
		inventario.setRebelde(rebelde);
		
		return inventario;
		
	}
	
	private Item getItem(Long id, int pontos, String nome) {
		var item = new Item();
		item.setId(id);
		item.setNome(nome);
		item.setPontos(new BigInteger(pontos + ""));
		return item;
	}
	
	private Rebelde getRebelde(String nome, Long id) {
		var rebelde = new RebeldeDTO();
		rebelde.setGenero("FEMININO");
		rebelde.setIdade(new BigInteger(30 + ""));
		
		rebelde.setNome(nome);
		
		var list = new ArrayList<Inventario>();
		var inve = new Inventario();
		var item = new Item();
		item.setId(id);
		inve.setItem(item);
		inve.setQtd(new BigInteger(1+""));
		
		list.add(inve);
		rebelde.setInventario(list);
		
		var local = new Localizacao();
		local.setId(3L);
		rebelde.setLocalizacao(local);
		
		var rebeldeEntity = rebelde.toRebelde();
		rebeldeEntity.setId(id);
		return rebeldeEntity;
	}
	
	
}
