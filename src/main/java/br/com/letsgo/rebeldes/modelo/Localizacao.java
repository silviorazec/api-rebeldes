package br.com.letsgo.rebeldes.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Schema(name = "Localizacao")
public class Localizacao {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Schema(name = "id", example = "12", type = "Long", description = "Identificador da localização")
	private Long id;
	@Schema(name = "longitude", example = "-234234", type = "String", description = "Coordenada longitude onde fica a base do rebelde", accessMode = Schema.AccessMode.READ_ONLY)
	private String longitude;
	@Schema(name = "latitude", example = "45567687", type = "String", description = "Coordenada latitude onde fica a base do rebelde", accessMode = Schema.AccessMode.READ_ONLY)
	private String latitude;
	@Schema(name = "galaxia", example = "Sistema Hoth", type = "String", description = "Nome da galaxia onde o rebelde está", accessMode = Schema.AccessMode.READ_ONLY)
	private String galaxia;
	@Schema(name = "base", example = "Base Echo", type = "String", description = "Base onde o rebelde está instalado", accessMode = Schema.AccessMode.READ_ONLY)
	private String base;
}
