package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
@Table
@EqualsAndHashCode(callSuper=true)
@DataTransferObject
public class AvaliacaoAntropometrica extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1136316925680643949L;

	// TODO verificar esses metodos 
	/**
	 * Realiza o calculo da densidade corporal de acordo com o protocolo 
	 * 
	 */
//	@Transient
//	public abstract void equacaoHomem();
	
	/**
	 * Realiza o calculo da densidade corporal de acordo com o protocolo 
	 * 
	 */
//	@Transient
//	public abstract void equacaoMulher();
	
	// medidas das dobras cutaneas
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = DobrasCutaneas.class,
//		mappedBy = "abstractEntityAvaliacaoAntropometrica",
		orphanRemoval = true
	)
	private DobrasCutaneas dobrasCutaneas;
//	
//	// dados de indice de massa corporal
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = true,
		targetEntity = IndiceMassaCorporal.class,
//		mappedBy = "abstractEntityAvaliacaoAntropometrica",
		orphanRemoval = true
	)
	private IndiceMassaCorporal indiceMassaCorporal;
//	
//	// gordura siri
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = true,
		targetEntity = PredicaoGorduraSiri.class,
//		mappedBy = "abstractEntityAvaliacaoAntropometrica",
		orphanRemoval = true
	)
	private PredicaoGorduraSiri predicaoGorduraSiri;
//	
//	// referencia da avaliacao fisica
//	@NotNull
//	@OneToOne(
//		cascade = CascadeType.MERGE,
//		fetch = FetchType.LAZY
////		optional = true,
////		targetEntity = AvaliacaoFisica.class,
////		mappedBy = "abstractEntityAvaliacaoAntropometrica",
////		orphanRemoval = false
//	)
//	private AvaliacaoFisica avaliacaoFisica;

	// ------------------------------------------------
	// ----------------- CONSTRUTORS ------------------
	// ------------------------------------------------
	

	/**
	 * @param id
	 * @param dobrasCutaneas
	 * @param indiceMassaCorporal
	 * @param predicaoGorduraSiri
	 */
	public AvaliacaoAntropometrica(
		Long id, 
		DobrasCutaneas dobrasCutaneas,
		IndiceMassaCorporal indiceMassaCorporal, 
		PredicaoGorduraSiri predicaoGorduraSiri
	) {
		super(id);
		this.dobrasCutaneas = dobrasCutaneas;
		this.indiceMassaCorporal = indiceMassaCorporal;
		this.predicaoGorduraSiri = predicaoGorduraSiri;
	}
	
	/**
	 * 
	 * @param id
	 */
	public AvaliacaoAntropometrica( Long id ) {
		super(id);
	}

	/**
	 * Constructor default
	 */
	public AvaliacaoAntropometrica() {
	}
	
}