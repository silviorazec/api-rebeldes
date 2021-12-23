package br.com.letsgo.rebeldes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.letsgo.rebeldes.modelo.Rebelde;

public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {

	List<Rebelde> findByNome(String nome);
	List<Rebelde> findByFielACausa(boolean fielAcausa);
	Optional<Rebelde> findByIdAndFielACausa(Long id, boolean fielAcausa);
  
}