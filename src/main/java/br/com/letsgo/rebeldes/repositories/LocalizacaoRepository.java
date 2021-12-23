package br.com.letsgo.rebeldes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.letsgo.rebeldes.modelo.Localizacao;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {

	@Query(value = "SELECT l.* FROM Localizacao l INNER JOIN Rebelde r on r.id_localizacao = l.id WHERE r.id = :idRebelde ", nativeQuery = true)
	public Localizacao getLocalizacaoByIdRebelde(Long idRebelde);
}