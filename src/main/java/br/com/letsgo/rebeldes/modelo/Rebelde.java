package br.com.letsgo.rebeldes.modelo;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Schema(name = "Rebelde")
public class Rebelde {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator ="rebelde_generator" )
	@TableGenerator(name = "rebelde_generator", table = "ID_REBELDE", pkColumnName = "ID", initialValue = 100)
	@Schema(name = "id", example = "1", type = "Long", description = "identificador do rebelde",accessMode = Schema.AccessMode.READ_ONLY)
	private Long id;
	@Schema(name = "nome", example = "LEA", type = "String", description = "Nome do Rebelde")
	private String nome;
	@Schema(name = "idade", example = "30", type = "Integer", description = "Idade do rebelde")
	private BigInteger idade;
	@Schema(name = "genero", example = "FEMININO", type = "String", description = "Gênero do rebelde")
	private String genero;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@Schema(name = "localizacao",  description = "Localização oficial do Rebelde",accessMode = Schema.AccessMode.READ_ONLY)
	@JoinColumn(name="id_localizacao", insertable = true, updatable = false)
	private Localizacao localizacao;
	@Schema(hidden = true)
	private boolean fielACausa = true;

	
}
