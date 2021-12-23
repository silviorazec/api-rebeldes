package br.com.letsgo.rebeldes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.Erro;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Localizacao;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.service.LocalizacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping({"v1/rebeldes"})
@Tag(description = "Serviços do objeto Localizacao", name = "Localizacao")
public class LocalizacaoController{
	
	@Autowired
	private LocalizacaoService localizacaoService;

	
	@Operation(description = "Recuperar localizacao de um rebelde", method = "GET")
	@ApiResponse(responseCode = "200", description = "Sucesso", 
					content = @Content(schema = @Schema(implementation =  Localizacao.class)))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Serviro", content = @Content(schema = @Schema(implementation = Erro.class)))
	@ApiResponse(responseCode = "404", description = "Nada encontrado")
	@GetMapping(path="/{id}/localizacao", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Localizacao> pesquisarRebeldes(@PathVariable("id") Long id) throws NotFoundException, RebeldeException {
		
		return ResponseEntity.ok(localizacaoService.getLocalizacaByIdRebelde(id));
	}
	

	@Operation(description = "Modifica a localização do rebelde", method = "PUT")
	@ApiResponse(responseCode = "200", description = "Rebelde criado",
					content = @Content(schema = @Schema(implementation = Rebelde.class)))
	@ApiResponse(responseCode = "404", description = "Nada encontrado", content = @Content(schema = @Schema(implementation = Void.class)))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Servirço", content = @Content(schema = @Schema(implementation = Erro.class)))
	@PutMapping(path="/{id}/localizacao", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Localizacao> cadastrarRebelde(@Valid @RequestBody Localizacao localizacao, @PathVariable("id") Long idRebelde) throws RebeldeException, NotFoundException, ConflitctException{
		
		return ResponseEntity.ok(localizacaoService.atualizarLocalizacao(localizacao,idRebelde));
	}
	

}
