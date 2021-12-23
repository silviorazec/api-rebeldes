package br.com.letsgo.rebeldes.service;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letsgo.rebeldes.dto.RebeldeDTO;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.exception.SeeOtherException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.repositories.InventarioRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@Service
public class RebeldeService {
	
	
	@Autowired
	private RebeldeRepository rebeldeRepository;
	@Autowired
	private InventarioRepository inventarioRepository;
	
	public URI salvarRebelde(RebeldeDTO rebeldeDTO, HttpServletRequest request) throws SeeOtherException, RebeldeException{
		var listRebelde = rebeldeRepository.findByNome(rebeldeDTO.getNome());
		
		try {
			if(listRebelde.isEmpty()) {
				var rebelde = rebeldeRepository.save(rebeldeDTO.toRebelde());
				inventarioRepository.saveAll(rebeldeDTO.toListInventarioCompleto(rebelde));
				return new URI(request.getRequestURI() + "/" + rebelde.getId());
			}else {
				throw new SeeOtherException("Nome do rebelde já cadastrado",listRebelde.get(0).getId());
			}
		}catch (SeeOtherException e) {
			throw e;
		} catch (Exception e) {
			throw new RebeldeException(e);
		}
	}
	
	public List<Rebelde> getRebeldes() throws NotFoundException, RebeldeException{
		try {
			var lista = rebeldeRepository.findByFielACausa(true);
			
			if(lista.isEmpty()) {
				throw new NotFoundException();
			}else {
				return lista;
			}
		}catch (NotFoundException e) {
			throw e;
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}
	
	public Rebelde getRebelde(Long id) throws NotFoundException, RebeldeException{
		
		try {
			var rebelde = rebeldeRepository.findByIdAndFielACausa(id,true);
			
			if(rebelde.isPresent()) {
				return rebelde.get();
			}else {
				throw new NotFoundException();
			}
		}catch (NotFoundException e) {
			throw e;
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}
	
	public List<Inventario> getInventario(Long id) throws NotFoundException, RebeldeException{
		
		try {
			var inventario = inventarioRepository.getByIdRebelde(id);
			
			if(!inventario.isEmpty()) {
				return inventario;
			}else {
				throw new NotFoundException();
			}
		}catch (NotFoundException e) {
			throw e;
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}
	
	

}
