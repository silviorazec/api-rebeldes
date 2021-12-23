package br.com.letsgo.rebeldes.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letsgo.rebeldes.dto.ItensDTO;
import br.com.letsgo.rebeldes.dto.NegociacaoDTO;
import br.com.letsgo.rebeldes.exception.BreakException;
import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.repositories.InventarioRepository;
import br.com.letsgo.rebeldes.repositories.ItemRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@Service
public class NegociacaoService {
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	InventarioRepository inventarioRepository;
	@Autowired
	RebeldeRepository rebeldeRepository;
	
	public void efetuar(NegociacaoDTO negociacao) throws ConflitctException, RebeldeException {
		
		verificarDisponibilidadeItens(negociacao.getIdRebeldeVendedor(), negociacao.getItensVendedor());
		verificarDisponibilidadeItens(negociacao.getIdRebeldeComprador(), negociacao.getItensComprador());
		
		verificarIguadadeDosPontos(negociacao);
	
		salvarSaidaInventario(negociacao.getIdRebeldeComprador(), negociacao.getItensComprador());
		salvarSaidaInventario(negociacao.getIdRebeldeVendedor(), negociacao.getItensVendedor());
		salvarEntradaInventario(negociacao.getIdRebeldeComprador(),negociacao.getItensVendedor());
		salvarEntradaInventario(negociacao.getIdRebeldeVendedor(),negociacao.getItensComprador());
	}
	
	private void salvarEntradaInventario(Long idRebelde, List<ItensDTO> itensNovos) throws RebeldeException {
		try {
			itensNovos.forEach(i ->{
				
				Inventario inventario = new Inventario();
				
				var inventarioOp = inventarioRepository.getByIdRebeldeAndIdItem(idRebelde, i.getIdItem());
				if(inventarioOp.isPresent()) {
					inventario = inventarioOp.get(); 
				}else {
					inventario.setRebelde(rebeldeRepository.findById(idRebelde).get());
					inventario.setItem(itemRepository.findById(i.getIdItem()).get());
				}
				
				inventario.setQtd(inventario.getQtd().add(i.getQtd()));
				inventarioRepository.save(inventario);
				
				
			});
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}
	
	private void salvarSaidaInventario(Long idRebelde, List<ItensDTO> itens) throws RebeldeException {
		try {
			itens.forEach(i->{
				var inventario = inventarioRepository.getByIdRebeldeAndIdItem(idRebelde, i.getIdItem()).get();
				inventario.setQtd(inventario.getQtd().subtract(i.getQtd()));
				inventarioRepository.save(inventario);
			});
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}

	
	private void verificarIguadadeDosPontos(NegociacaoDTO negociacao) throws ConflitctException {
		
		var totalPontosItemVendedor = getTotalPontos(negociacao.getItensVendedor());
		var totalPontosItemComprador= getTotalPontos(negociacao.getItensComprador());
		
		if(!totalPontosItemComprador.equals(totalPontosItemVendedor)) {
			throw new ConflitctException("Os pontos são diferentes");
		}
		
	}
	
	private BigInteger getTotalPontos(List<ItensDTO> itens) {
		BigInteger pontos = BigInteger.ZERO;
		for(ItensDTO i : itens) {
			pontos = pontos.add((i.getQtd().multiply(itemRepository.findById(i.getIdItem()).get().getPontos())));
		}
		
		return pontos;
		
	}


	private void verificarDisponibilidadeItens(Long idRebelde, List<ItensDTO> itens) throws ConflitctException, RebeldeException {
		try {
			itens.forEach(i -> {

				var inventario = inventarioRepository.getByIdRebeldeAndIdItem(idRebelde, i.getIdItem());
				if(inventario != null && inventario.isPresent()) {
					if(inventario.get().getQtd().compareTo(i.getQtd()) == -1) {
						throw new BreakException();
					}
				}else {
					throw new BreakException();
				}
			});
		}catch (BreakException e) {
			throw new ConflitctException("Alguns Itens negociados não estão disponíveis no inventário de pelo menos um dos negociadores");
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
		

	}
}
