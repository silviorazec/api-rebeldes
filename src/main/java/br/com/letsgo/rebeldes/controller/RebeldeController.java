package br.com.letsgo.rebeldes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letsgo.rebeldes.dto.InventarioDTO;
import br.com.letsgo.rebeldes.dto.RebeldeDTO;
import br.com.letsgo.rebeldes.exception.Erro;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.exception.SeeOtherException;
import br.com.letsgo.rebeldes.modelo.Inventario;
import br.com.letsgo.rebeldes.modelo.Rebelde;
import br.com.letsgo.rebeldes.service.RebeldeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping({"v1/rebeldes"})
@Tag(description = "Serviços do objeto Rebelde", name = "Rebelde")
public class RebeldeController{
	
	@Autowired
	private RebeldeService rebeldeService;

	@Autowired
	private HttpServletRequest servletRequest;
	
	@Operation(description = "Recuperar lista rebeldes", method = "GET")
	@ApiResponse(responseCode = "200", description = "Sucesso", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Rebelde.class))))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Serviro", content = @Content(schema = @Schema(implementation = Erro.class)))
	@ApiResponse(responseCode = "404", description = "Nada encontrado", content = @Content(schema = @Schema(implementation = Void.class)))
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Rebelde>> pesquisarRebeldes() throws NotFoundException, RebeldeException {
		
		return ResponseEntity.ok(rebeldeService.getRebeldes());
	}
	
	@Operation(description = "Recuperar lista rebeldes", method = "GET")
	@ApiResponse(responseCode = "200", description = "Sucesso", 
					content = @Content(schema = @Schema(implementation = Rebelde.class)))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Serviro", content = @Content(schema = @Schema(implementation = Erro.class)))
	@ApiResponse(responseCode = "404", description = "Nada encontrado", content = @Content(schema = @Schema(implementation = Void.class)))
	@GetMapping(path ="/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rebelde> getRebelde(@Parameter(name = "ide", example = "1", required = true, description = "Identificador do rebelde") 
											  @PathVariable("id") Long id) throws NotFoundException, RebeldeException {
		
		return ResponseEntity.ok(rebeldeService.getRebelde(id));
	}
	
	
	
	@Operation(description = "Cadastra um novo rebelde", method = "POST")
	@ApiResponse(responseCode = "201", description = "Rebelde criado",
					headers =@Header(name = "location",description = "Link para o do novo rebelde criado"),
					content = @Content(schema = @Schema(implementation = Rebelde.class)))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Servirço", content = @Content(schema = @Schema(implementation = Erro.class)))
	@ApiResponse(responseCode = "302", description = "See Other: Rebelde já cadastrado. Será redirecionado para o rebelde existente",
				headers = @Header(name = "location",description = "Link para o do novo recurso"),
				content = @Content(schema = @Schema(implementation = Rebelde.class)))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> cadastrarRebelde(
												@Valid @RequestBody RebeldeDTO rebelde) throws SeeOtherException, RebeldeException{
		
		return ResponseEntity.created(
									rebeldeService.salvarRebelde(rebelde,servletRequest)
									).build();
	}
	
	
	@Operation(description = "Recuperar Inventario completo de um rebelde", method = "GET")
	@ApiResponse(responseCode = "200", description = "Sucesso", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = InventarioDTO.class))))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Serviro", content = @Content(schema = @Schema(implementation = Erro.class)))
	@ApiResponse(responseCode = "404", description = "Nada encontrado", content = @Content(schema = @Schema(implementation = Void.class)))
	@GetMapping(path ="/{id}/inventario" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Inventario>> getInventario(@Parameter(name = "ide", example = "1", required = true, description = "Identificador do rebelde") 
															@PathVariable("id") Long id) throws NotFoundException, RebeldeException {
		
		return ResponseEntity.ok(rebeldeService.getInventario(id));
	}
	

}
