package br.com.letsgo.rebeldes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.repositories.LocalizacaoRepository;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;

@Service
public class LocalizacaoService {
	
	
	@Autowired
	private LocalizacaoRepository localizacaoRepository;
	
	@Autowired
	private RebeldeRepository rebeldeRepository;
	
	public Localizacao getLocalizacaByIdRebelde(Long idRebelde) throws NotFoundException, RebeldeException {
		try {
			var localizacao = localizacaoRepository.getLocalizacaoByIdRebelde(idRebelde);
			if(localizacao != null) {
				return localizacao;
			}else {
				throw new NotFoundException();
			}
		}catch (NotFoundException e) {
			throw e;
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}
	
	public Localizacao atualizarLocalizacao(Localizacao localizacao, Long idRebelde) throws  RebeldeException, NotFoundException, ConflitctException {
		

		try {
			
			var rebeldeOP = rebeldeRepository.findById(idRebelde);
			if(!rebeldeOP.isPresent()) {
				throw new NotFoundException();
			}
			
			var localDestino = localizacaoRepository.findById(localizacao.getId());
			

			
			if(!localDestino.isPresent()) {
				throw new ConflitctException("Localização não encontrada");
			}
			
			var rebelde = rebeldeOP.get();
			rebelde.setLocalizacao(localDestino.get());
			rebeldeRepository.save(rebelde);
			return localDestino.get();
		}catch (ConflitctException e) {
			throw e;
		}catch (NotFoundException e) {
				throw e;
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
	}

}
