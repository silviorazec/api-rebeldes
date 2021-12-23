package br.com.letsgo.rebeldes.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Erro {


	private int codigo;
	private String mensagem;
	
}
