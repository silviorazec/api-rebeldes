package br.com.letsgo.rebeldes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letsgo.rebeldes.exception.Erro;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.modelo.Item;
import br.com.letsgo.rebeldes.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping({"v1/itens"})
@Tag(description = "Serviços do objeto Item", name = "Item")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@Operation(description = "Recuperar lista itens e seus pontos", method = "GET")
	@ApiResponse(responseCode = "200", description = "Sucesso", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Item.class))))
	@ApiResponse(responseCode = "500", description = "Erro Interno do Serviro", content = @Content(schema = @Schema(implementation = Erro.class)))
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Item>> getItens() throws RebeldeException{
		
		return ResponseEntity.ok(
								itemService.getItens()
								);
	}

}
