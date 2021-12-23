package br.com.letsgo.rebeldes.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Traicao;
import br.com.letsgo.rebeldes.repositories.RebeldeRepository;
import br.com.letsgo.rebeldes.repositories.TraicaoRepository;

@Service
public class TraicaoService {
	
	
	@Autowired
	private TraicaoRepository traicaoRepository;
	@Autowired
	private RebeldeRepository rebeldeRepository;
	
	public Traicao registrarTraicao(Traicao traicao) throws  RebeldeException, ConflitctException {
		

		try {	
			if(isDenunciaValida(traicao.getIdDenunciado(), traicao.getIdDenunciante())){
				
				traicao = traicaoRepository.save(traicao);
				
				if(isTraicaoConfirmada(traicao.getIdDenunciado())) {
					registrarRebeldeTraidor(traicao.getIdDenunciado());
				}
				
				return traicao;
			}else {
				throw new ConflitctException("Denúncia não é válida: Denunciado ou Denunciante não existe, ou o Denunciante já reportou essa traição");
			}
		}catch (ConflitctException e) {
			throw e;
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
		
	}
	
	private void registrarRebeldeTraidor(Long idTraidor) {
		var traidor = rebeldeRepository.findById(idTraidor).get();
		traidor.setFielACausa(false);
		rebeldeRepository.save(traidor);
	}
	
	
	private boolean hasEnvolvidos(Long idDenunciado, Long idDenunciante) {
		var envolvidos = new ArrayList<Long>();
		envolvidos.add(idDenunciado);
		envolvidos.add(idDenunciante);
		return rebeldeRepository.findAllById(envolvidos).size() == 2;
	}
	
	private boolean isTraicaoConfirmada(Long idDenunciado) {
		return  traicaoRepository.findByIdDenunciado(idDenunciado).size() >= 3;
	}
	
	private boolean isNovoDenunciante(Long idDenunciado, Long idDenunciante) {
		return traicaoRepository.findByIdDenunciadoAndIdDenunciante(idDenunciado, idDenunciante).isEmpty();
	}
	
	private boolean isDenunciaValida(Long idDenunciado, Long idDenunciante) {
		return    hasEnvolvidos(idDenunciado, idDenunciante) &&
				isNovoDenunciante(idDenunciado, idDenunciante);
	}


}
