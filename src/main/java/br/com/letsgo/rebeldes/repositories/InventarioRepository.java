package br.com.letsgo.rebeldes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.letsgo.rebeldes.modelo.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
	
	@Query(value = "SELECT i.* FROM Inventario i INNER JOIN Rebelde r on i.id_rebelde = r.id WHERE r.id = :idRebelde", nativeQuery = true)
	List<Inventario> getByIdRebelde(Long idRebelde);
	
	@Query(value = "SELECT i.* FROM Inventario i INNER JOIN Rebelde r on i.id_rebelde = r.id WHERE r.id = :idRebelde AND i.id_item = :idItem", nativeQuery = true)
	Optional<Inventario> getByIdRebeldeAndIdItem(Long idRebelde, Long idItem);

	@Query(value = "SELECT i.* FROM Inventario i WHERE i.id_item = :idItem", nativeQuery = true)
	List<Inventario> findByIdItem(Long idItem);
	
	@Query(value = "SELECT i.* FROM Inventario i INNER JOIN Rebelde r on i.id_rebelde = r.id WHERE r.fielACausa = 1 AND i.id_item = :idItem", nativeQuery = true)
	List<Inventario> findByRebeldeFielAndIdItem(Long idItem);

	


}