package br.com.letsgo.rebeldes.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.repositories.LocalizacaoRepository;

@Component
public class LocalizacaoDeserializer extends StdDeserializer<Localizacao> { 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	LocalizacaoRepository localizacaoRepository;


	public LocalizacaoDeserializer() { 
        this(null); 
    } 

    public LocalizacaoDeserializer(Class<?> vc) { 
        super(vc); 
    }

 
	@Override
	public Localizacao deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		var id = Long.parseLong(jp.getCodec().readTree(jp).toString());
        return localizacaoRepository.findById(id).get();
	}
}