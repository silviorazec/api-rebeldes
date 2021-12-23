package br.com.letsgo.rebeldes.modelo;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.letsgo.rebeldes.utils.ItemDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Schema(name = "Inventario")
public class Inventario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator = "inventario_generator")
	@TableGenerator(name = "inventario_generator", table = "ID_INVENTARIO", pkColumnName = "ID", initialValue = 100)
	@Schema(name = "id", example = "13", type = "Long", description = "identificador do inventário")
	private Long id;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_item",insertable = true,updatable = true)
	@JsonDeserialize(using = ItemDeserializer.class)
	@Schema(name = "item", example = "LEA", type = "String", description = "Nome do item",accessMode = Schema.AccessMode.READ_ONLY)
	private Item item;
	@Schema(hidden = true)
	@ManyToOne()
	@JoinColumn(name="id_rebelde", nullable=false, insertable = true, updatable = true)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Rebelde rebelde;
	@Schema(name = "qtd", example = "456", type = "Long", description = "Total de itens com o rebelde")
	private BigInteger qtd = BigInteger.ZERO;

}
