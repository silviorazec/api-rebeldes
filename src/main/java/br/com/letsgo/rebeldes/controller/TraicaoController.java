package br.com.letsgo.rebeldes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.Erro;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Traicao;
import br.com.letsgo.rebeldes.service.TraicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping({"v1/traicoes"})
@Tag(description = "Serviços do objeto Traicao", name = "Traicao")
public class TraicaoController {
	
	@Autowired
	TraicaoService traicaoService;
	
	@Operation(description = "Cadastra uma nova denúncia", method = "POST")
	@ApiResponse(responseCode = "200", description = "Denúncia criada",
					content = @Content(schema = @Schema(implementation = Traicao.class)))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Servirço", content = @Content(schema = @Schema(implementation = Erro.class)))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Traicao> cadastrarRebelde(@Valid @RequestBody Traicao traicao) throws RebeldeException,ConflitctException{
		
		return ResponseEntity.ok(
								traicaoService.registrarTraicao(traicao)
								);
	}

}
