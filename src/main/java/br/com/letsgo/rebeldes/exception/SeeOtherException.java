package br.com.letsgo.rebeldes.exception;

import lombok.Getter;

@Getter
public class SeeOtherException extends Throwable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	public SeeOtherException(String mensagem,Long id) {
		super(mensagem);
		this.id = id;
	}

}
