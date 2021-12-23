package br.com.letsgo.rebeldes.modelo;

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
@Schema(name = "Traicao")
public class Traicao {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Schema(name = "id", example = "13", type = "Long", description = "Id da denúncia",accessMode = Schema.AccessMode.READ_ONLY)
	private Long id;
	@Schema(name = "idDenunciante", example = "123", type = "Long", description = "Id do Rebelde que faz a denúndia")
	private Long idDenunciante;
	@Schema(name = "idDenunciado", example = "145", type = "Long", description = "Id do Rebelde denunciado")
	private Long idDenunciado;
}
