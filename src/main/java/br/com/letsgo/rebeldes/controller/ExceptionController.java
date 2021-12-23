package br.com.letsgo.rebeldes.controller;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.letsgo.rebeldes.exception.ConflitctException;
import br.com.letsgo.rebeldes.exception.Erro;
import br.com.letsgo.rebeldes.exception.NotFoundException;
import br.com.letsgo.rebeldes.exception.RebeldeException;
import br.com.letsgo.rebeldes.exception.SeeOtherException;

@ControllerAdvice
public class ExceptionController {
	
	
	
	@Autowired
	private HttpServletRequest servletRequest;
	

	
	@ExceptionHandler(value = RebeldeException.class )
	public ResponseEntity<Erro> getInternalServerErrorException( RebeldeException ex, WebRequest request){
		var LOG = Logger.getLogger(RebeldeException.class);
		LOG.error(ex,ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Erro.builder()
																						.codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
																						.mensagem("Internal Server Erro").build());
	}
	
	@ExceptionHandler(value = NotFoundException.class )
	public ResponseEntity<Erro> getNotfoundException( NotFoundException ex, WebRequest request){
		var LOG = Logger.getLogger(NotFoundException.class);
		LOG.info("A consulta não retornou nenhum dado");
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(value = SeeOtherException.class )
	public ResponseEntity<Void> getSeeOtherException( SeeOtherException ex, WebRequest request){
		var LOG = Logger.getLogger(SeeOtherException.class);
		LOG.info("Houve tentativa de cadastro de rebelde já cadastrado. Retornando 302  (SEE OTHER) para o cliente");
		
		return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", servletRequest.getRequestURI() + "/ " + ex.getId()).build();
	}
	
	@ExceptionHandler(value = ConflitctException.class )
	public ResponseEntity<Erro> getConflictException( ConflitctException ex, WebRequest request){
		var LOG = Logger.getLogger(ConflitctException.class);
		LOG.warn(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Erro.builder()
																			.codigo(HttpStatus.CONFLICT.value())
																			.mensagem(ex.getMessage()).build());
	}
	
	

}
