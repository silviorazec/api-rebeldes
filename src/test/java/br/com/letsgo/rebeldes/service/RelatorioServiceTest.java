package br.com.letsgo.rebeldes.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.repositories.InventarioRepository;
import br.com.letsgo.rebeldes.repositories.ItemRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@SpringBootTest
public class RelatorioServiceTest {

	@Mock
	RebeldeRepository rebeldeRepository;
	
	@Mock
	ItemRepository itemRepository;
	
	@Mock
	InventarioRepository inventarioRepository;
	
	@InjectMocks
	RelatorioService relatorioService;
	
	@Test
	public void getRelatorioConsolidadoSucesso() throws RebeldeException{
		
		var inventario = new Inventario();
		inventario.setId(1L);
		inventario.setItem(new Item());
		inventario.setQtd(new BigInteger("4"));
		
		var list = new ArrayList<Inventario>();
		list.add(inventario);
		
		var listRebelde = new ArrayList<Rebelde>();
		listRebelde.add(new Rebelde());
		
		Mockito.when(itemRepository.findAll()).thenReturn(Arrays.asList(getItem(3L,10,"AGUA")));
		Mockito.when(inventarioRepository.findByRebeldeFielAndIdItem(Mockito.anyLong())).thenReturn(list);
		Mockito.when(rebeldeRepository.findAll()).thenReturn(listRebelde);
		Mockito.when(rebeldeRepository.findByFielACausa(true)).thenReturn(listRebelde);
		Mockito.when(rebeldeRepository.findByFielACausa(false)).thenReturn(new ArrayList<Rebelde>());
		Mockito.when(rebeldeRepository.findByFielACausa(false)).thenReturn(new ArrayList<Rebelde>());
		Mockito.when(inventarioRepository.getByIdRebelde(Mockito.anyLong())).thenReturn(list);
		
		assertThat(relatorioService.getRelatorioConsolidado()).isNotEqualTo(null);
	
		 
	}
	
	@Test
	public void getRelatorioConsolidadoErro() throws RebeldeException{
		
		var inventario = new Inventario();
		inventario.setId(1L);
		inventario.setItem(new Item());
		inventario.setQtd(new BigInteger("4"));
		
		var list = new ArrayList<Inventario>();
		list.add(inventario);
		
		Mockito.when(itemRepository.findAll()).thenThrow(NullPointerException.class);
		Mockito.when(inventarioRepository.findByRebeldeFielAndIdItem(Mockito.anyLong())).thenReturn(list);
		Mockito.when(rebeldeRepository.findAll()).thenReturn(new ArrayList<Rebelde>());
		Mockito.when(rebeldeRepository.findByFielACausa(true)).thenReturn(new ArrayList<Rebelde>());
		Mockito.when(rebeldeRepository.findByFielACausa(false)).thenReturn(new ArrayList<Rebelde>());
		Mockito.when(rebeldeRepository.findByFielACausa(false)).thenReturn(new ArrayList<Rebelde>());
		Mockito.when(inventarioRepository.getByIdRebelde(Mockito.anyLong())).thenReturn(list);

		Assertions.assertThrows(RebeldeException.class, () -> relatorioService.getRelatorioConsolidado());
	
		 
	}

	
	private Item getItem(Long id, int pontos, String nome) {
		var item = new Item();
		item.setId(id);
		item.setNome(nome);
		item.setPontos(new BigInteger(pontos + ""));
		return item;
	}
	

	
}
