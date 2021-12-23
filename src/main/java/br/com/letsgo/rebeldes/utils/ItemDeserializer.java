package br.com.letsgo.rebeldes.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.repositories.ItemRepository;

@Component
public class ItemDeserializer extends StdDeserializer<Item> { 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	ItemRepository itemRepository;


	public ItemDeserializer() { 
        this(null); 
    } 

    public ItemDeserializer(Class<?> vc) { 
        super(vc); 
    }

 
	@Override
	public Item deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		var id = Long.parseLong(jp.getCodec().readTree(jp).toString());
        return itemRepository.findById(id).get();
	}
}