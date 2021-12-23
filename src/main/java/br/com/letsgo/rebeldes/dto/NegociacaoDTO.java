package br.com.letsgo.rebeldes.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegociacaoDTO {
	
	private Long idRebeldeComprador;
	private Long idRebeldeVendedor;
	private List<ItensDTO> itensVendedor;
	private List<ItensDTO> itensComprador;

}
