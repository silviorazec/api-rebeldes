package br.com.letsgo.rebeldes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letsgo.rebeldes.dto.ConsolidadoDTO;
import br.com.letsgo.rebeldes.exception.Erro;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping({"v1/relatorios"})
@Tag(description = "Serviços de Relatórios", name = "Relatório")
public class RelatorioController {
	
	@Autowired
	RelatorioService relatorioService;
	
	@Operation(description = "Recupera um relatório consolidado", method = "GET")
	@ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
					content = @Content(schema = @Schema(implementation = ConsolidadoDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Servirço", content = @Content(schema = @Schema(implementation = Erro.class)))
	@GetMapping(path="/consolidado",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConsolidadoDTO> getRelatorioConsolidado() throws RebeldeException{
		
		return ResponseEntity.ok(
								relatorioService.getRelatorioConsolidado()
								);
	}

}
