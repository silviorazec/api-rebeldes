package br.com.letsgo.rebeldes.modelo;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Schema(name = "Item")
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Schema(name = "id", example = "12", type = "Long", description = "Identificador do item")
	private Long id;
	@Schema(name = "nome", example = "ÁGUA", type = "String", description = "Nome do intem")
	private String nome;
	@Schema(name = "pontos", example = "1", type = "Integer", description = "Pontuação do item")
	private BigInteger pontos;
	
}
