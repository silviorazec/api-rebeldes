package br.com.letsgo.rebeldes.dto;

import java.math.BigInteger;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.utils.ItemDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "InventarioDTO")
public class InventarioDTO {
	
	
	@Schema(name = "id", example = "13", type = "Long", description = "identificador do inventário")
	private Long id;
	@JsonDeserialize(using = ItemDeserializer.class)
	@Schema(name = "item", example = "LEA", type = "String", description = "Nome do item",accessMode = Schema.AccessMode.READ_ONLY)
	private Item item;
	@Schema(name = "qtd", example = "456", type = "Long", description = "Total de itens com o rebelde")
	private BigInteger qtd = BigInteger.ZERO;

}
