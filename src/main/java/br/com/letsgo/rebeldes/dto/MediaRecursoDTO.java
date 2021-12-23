package br.com.letsgo.rebeldes.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Média Recurso")
public class MediaRecursoDTO {
	@Schema(name = "recurso", example = "ÁGUA", type = "String", description = "Nome do recurso em posse do rebelde")
	private String recurso;
	@Schema(name = "media", example = "10", type = "Decimal", description = "Média do recurso em posse por rebelde")
	private BigDecimal media;
}
