package br.com.letsgo.rebeldes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.letsgo.rebeldes.modelo.Traicao;

public interface TraicaoRepository extends JpaRepository<Traicao, Long> {

	
	List<Traicao> findByIdDenunciado(Long idDenuciado);
	Optional<Traicao> findByIdDenunciadoAndIdDenunciante(Long idDenuciado, Long idDenunciante);
	
	
}