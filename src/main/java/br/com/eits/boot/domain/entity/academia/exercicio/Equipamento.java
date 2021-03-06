package br.com.eits.boot.domain.entity.academia.exercicio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.io.FileTransfer;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=Equipamento.class)
public class Equipamento extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4764177726743656960L;

	// --------------------------------------
	// ----- ATRIBUTOS ----------------------
	// --------------------------------------
	
	// descricao ou nome do exercicio
	@NotEmpty
	@Column(nullable = false)
	private String descricao;
	
	// imagem ilustrativa do exercicio
	@Lob
	@Column
	private byte[] imagem;
	
	@Transient
	private FileTransfer imagemFileTransfer; 
	
//	jcr
//	filetransfer
	
	// status, se está ativo ou não, se não estiver não pode ser usado em exercicios
	@NotNull
	@Column(nullable = false)
	private Boolean isAtivo;

	// ---------------------------------------
	// -------- CONSTRUCTORS -----------------
	// ---------------------------------------
	
	/**
	 * Constructor default
	 */
	public Equipamento(){
		super();
	}
	
	/**
	 * 
	 * Constructor somente com id
	 * 
	 * @param id
	 * 
	 */
	public Equipamento( Long id ){
		super(id);
	}
	
	/**
	 * 
	 * Constructor com todos os campos 
	 *  
	 * @param descricao
	 * @param imagem
	 * @param isAtivo
	 */
	public Equipamento(String descricao, byte[] imagem, Boolean isAtivo) {
		super();
		this.descricao = descricao;
		this.imagem = imagem;
		this.isAtivo = isAtivo;
	}
	
	
	
}
