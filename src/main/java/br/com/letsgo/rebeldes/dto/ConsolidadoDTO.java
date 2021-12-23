package br.com.letsgo.rebeldes.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Relatorio")
public class ConsolidadoDTO {
	
	@Schema(name = "percentualTraidores", example = "1", type = "Decimal", description = "Percentual de traidores denunciados com denúncia confirmada")
	private BigDecimal percentualTraidores;
	@Schema(name = "percentualRebeldes", example = "13", type = "Decimal", description = "Percentual de rebeldes fiéis à causa")
	private BigDecimal percentualRebeldes;
	@ArraySchema(schema = @Schema(implementation = MediaRecursoDTO.class))
	private List<MediaRecursoDTO> mediaRecursoPorRebelde;
	@Schema(name = "pontosPerdidosTraicao", example = "10", type = "Inteiro", description = "Total de pontos perdidos com as traições confirmadas")
	private BigInteger pontosPerdidosTraicao;

}
