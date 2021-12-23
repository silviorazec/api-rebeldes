package br.com.letsgo.rebeldes.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letsgo.rebeldes.dto.ConsolidadoDTO;
import br.com.letsgo.rebeldes.dto.MediaRecursoDTO;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.repositories.InventarioRepository;
import br.com.letsgo.rebeldes.repositories.ItemRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@Service
public class RelatorioService {
	
	@Autowired
	RebeldeRepository rebeldeRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	InventarioRepository inventarioRepository;
	
	
	public ConsolidadoDTO getRelatorioConsolidado() throws RebeldeException {
		try {
			var consolidado = new ConsolidadoDTO();
			consolidado.setMediaRecursoPorRebelde(getMediaRecursosPorRebelde());
			consolidado.setPercentualRebeldes(getPercentualRebeldes());
			consolidado.setPercentualTraidores(getPercentualTraidores());
			consolidado.setPontosPerdidosTraicao(getTotalPontosPerdidosComTraicao());
			
			return consolidado;
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}
	
	private BigDecimal getPercentualTraidores() {
		int totalRebeldes = rebeldeRepository.findAll().size();
		int totalTraidores = rebeldeRepository.findByFielACausa(false).size();
		return new BigDecimal((totalTraidores * 100) / totalRebeldes );
	}
	
	private BigDecimal getPercentualRebeldes() {
		int totalRebeldes = rebeldeRepository.findAll().size();
		int totalFieis = rebeldeRepository.findByFielACausa(true).size();
		return new BigDecimal((totalFieis * 100) / totalRebeldes );
	}
	
	private List<MediaRecursoDTO> getMediaRecursosPorRebelde(){
		var itens = itemRepository.findAll();
		List<MediaRecursoDTO>  lista = new ArrayList<MediaRecursoDTO>();
		List<Inventario> listInventario = new ArrayList<Inventario>();
		itens.forEach(i ->{
			listInventario.clear();
			listInventario.addAll(inventarioRepository.findByRebeldeFielAndIdItem(i.getId()));
			int somaTotal = listInventario.stream().mapToInt(inv -> inv.getQtd().intValue()).sum();
			var mediaRecurso = new MediaRecursoDTO();
			mediaRecurso.setRecurso(i.getNome());
			mediaRecurso.setMedia(new BigDecimal(somaTotal / listInventario.size()));
			lista.add(mediaRecurso);
		});
		
		return lista;
	}
	
	private BigInteger getTotalPontosPerdidosComTraicao() {
		var traidores = rebeldeRepository.findByFielACausa(false);
		List<BigInteger> somasIndividuais = new ArrayList<BigInteger>();
		traidores.forEach(t ->{
			List<Inventario> inventario = inventarioRepository.getByIdRebelde(t.getId());
			somasIndividuais.add(new BigInteger(inventario.stream().mapToInt(inv -> inv.getQtd().intValue()).sum() + ""));
		});
		
		return new BigInteger(somasIndividuais.stream().mapToInt(s -> s.intValue()).sum() + "");
	}
	
	

}
