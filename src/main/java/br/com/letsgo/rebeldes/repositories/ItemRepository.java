package br.com.letsgo.rebeldes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.letsgo.rebeldes.modelo.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}