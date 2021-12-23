package br.com.letsgo.rebeldes.exception;

public class RebeldeException extends Exception{


	private static final long serialVersionUID = 1L;

	public RebeldeException(Exception e) {
		super(e);
	}
	
	public RebeldeException(String msg) {
		super(msg);
	}
}
