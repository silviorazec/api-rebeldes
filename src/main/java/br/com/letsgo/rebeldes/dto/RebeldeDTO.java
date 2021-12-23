package br.com.letsgo.rebeldes.dto;


import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.utils.LocalizacaoDeserializer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "RebeldeInput")
public class RebeldeDTO {


	@Schema(name = "id", example = "1", type = "Long", description = "identificador do rebelde",accessMode = Schema.AccessMode.READ_ONLY)
	private Long id;
	@Schema(name = "nome", example = "LEA", type = "String", description = "Nome do Rebelde")
	private String nome;
	@Schema(name = "idade", example = "30", type = "Integer", description = "Idade do rebelde")
	private BigInteger idade;
	@Schema(name = "genero", example = "FEMININO", type = "String", description = "Gênero do rebelde")
	private String genero;
	@JsonDeserialize(using = LocalizacaoDeserializer.class)
	@Schema(name = "localizacao",  description = "Localização oficial do Rebelde", type= "Long", example = "1")
	private Localizacao localizacao;
	@ArraySchema(schema = @Schema(implementation = InventarioDTO.class))
	private List<Inventario> inventario;
	

	public Rebelde toRebelde() {
		var rebelde = new Rebelde();
		rebelde.setFielACausa(true);
		rebelde.setGenero(genero);
		rebelde.setLocalizacao(localizacao);
		rebelde.setNome(nome);
		
		return rebelde;
	}
	
	public List<Inventario> toListInventarioCompleto(Rebelde rebelde){
		inventario.forEach(i -> {
			i.setRebelde(rebelde);
		});
		return inventario;
		
	}
	
	

}
