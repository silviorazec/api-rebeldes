package br.com.letsgo.rebeldes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.repositories.ItemRepository;

@Service
public class ItemService {
	
	
	@Autowired
	private ItemRepository itemRepository;

	
	public List<Item> getItens()throws  RebeldeException {
		

		try {	
			return itemRepository.findAll();
	
		}catch (Exception e) {
			throw new RebeldeException(e);
		}
		
	}
	
	
}
