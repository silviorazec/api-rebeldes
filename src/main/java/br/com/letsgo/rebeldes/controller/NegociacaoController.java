package br.com.letsgo.rebeldes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letsgo.rebeldes.dto.NegociacaoDTO;
import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.Erro;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.service.NegociacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping({"v1/negociacoes"})
@Tag(description = "Serviços do objeto Traicao", name = "Negocicacao")
public class NegociacaoController {
	
	@Autowired
	NegociacaoService negociacaoService;
	
	@Operation(description = "Realiza uma negociacao de itens", method = "PUT")
	@ApiResponse(responseCode = "204", description = "Negociacao Realizada")
	@ApiResponse(responseCode = "500", description = "Erro Interno do Servirço", content = @Content(schema = @Schema(implementation = Erro.class)))
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> cadastrarRebelde(@Valid @RequestBody NegociacaoDTO negociacao) throws RebeldeException,ConflitctException{
		
		negociacaoService.efetuar(negociacao);
		return ResponseEntity.noContent().build();
	}

}
