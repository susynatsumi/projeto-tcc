package br.com.eits.boot.domain.entity.account;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.boot.domain.entity.academia.pessoa.Genero;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 */
@Data
@Entity
@Audited
@Table
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@DataTransferObject
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=Pessoa.class)
public class Pessoa extends AbstractEntity implements Serializable, UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4052986759552589018L;

	/*-------------------------------------------------------------------
	 *				 		     ATRIBUTOS
	 *-------------------------------------------------------------------*/
	
	/**
	 * Nome da pessoa
	 */
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String nome;

	/**
	 * email, campo não é login
	 */
	@Email
	@NotEmpty
	@Column(nullable = false, length = 144, unique = true)
	private String email;
	
	/**
	 * login para sistema ou aplicativo, pode ser null quando a pessoa não tiver acesso
	 */
	@Column(unique = true)
	private String login;
	
	/**
	 * senha, poderá ser null da mesma forma que o login 
	 */
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	@NotBlank
	@Column(length = 100)
	private String senha;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Genero genero;
	
	/**
	 * Data de nascimento da pessoa
	 */
	@NotNull
	@Column(nullable = false)
	private LocalDate dataNascimento;

	/**
	 * objetivo da pessoa, pode ser um texto longo
	 */
	@Column(length = 500)
	private String objetivo;

	/**
	 * Para indicar que o usuário está ativo ou inativo
	 * Se estiver false o usuário não deve poder acessar o sistema
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean isAtivo;


	/**
	 * Papel do usuário, para indicar certos privilégios
	 */
	@NotNull
	@ElementCollection(fetch = FetchType.EAGER, targetClass= Papel.class)
	@Enumerated(EnumType.ORDINAL)
	@CollectionTable
	@Column(nullable = false)
	private Set<Papel> papeis;
	
	/**
	 * 
	 */
	private OffsetDateTime lastLogin;

	/**
	 * Token para login na api
	 */
	@Column( nullable = true , length = 500 )
	private String tokenJwt;
	
	@Transient
	private String senhaAntiga;
	
	// para listagem de avaliacao física
	@OneToMany(
		fetch = FetchType.LAZY,
		mappedBy = "pessoa",
		orphanRemoval = false,
		targetEntity = AvaliacaoFisica.class
	)
	private List<AvaliacaoFisica> avaliacoesFisicas;
	
	/*-------------------------------------------------------------------
	 *							CONSTRUCTORS
	 *-------------------------------------------------------------------*/

	/**
	 * default
	 */
	public Pessoa(){
		
	}
	
	/**
	 * 
	 * @param id
	 */
	public Pessoa( long id ){
		this.id = id;
	}

	/**
	 * 
	 * @param id
	 * @param nome
	 * @param email
	 * @param isAtivo
	 * @param papel
	 * @param senha
	 * @param dataNascimento
	 * @param genero
	 */
	public Pessoa( 
			Long id,
			String nome,
			String email, 
			Boolean isAtivo,
			Set<Papel> papeis, 
			String senha, 
			LocalDate dataNascimento ,
			Genero genero
	){
		super( id );
		this.email = email;
		this.nome = nome;
		this.isAtivo = isAtivo;
		this.senha = senha;
		this.papeis = papeis;
		this.dataNascimento = dataNascimento;
		this.genero = genero;
	}
	
	public Pessoa( 
			Long id,
			String nome,
			String email, 
			String login,
			String senha,
			Boolean isAtivo,
			Set<Papel> papeis, 
			LocalDate dataNascimento ,
			Genero genero,
			String objetivo,
			OffsetDateTime lastLogin,
			OffsetDateTime created,
			OffsetDateTime updated,
			String tokenJwt
	){
		super( id );
		this.email = email;
		this.nome = nome;
		this.login = login;
		this.isAtivo = isAtivo;
		this.senha = senha;
		this.papeis = papeis;
		this.dataNascimento = dataNascimento;
		this.genero = genero;
		this.objetivo = objetivo;
		this.lastLogin = lastLogin;
		this.created = created;
		this.updated = updated;
		this.tokenJwt = tokenJwt;
	}
	
	/*-------------------------------------------------------------------
	 *							BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * retorna as permissoes
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	public Set<GrantedAuthority> getAuthorities()
	{

		if ( this.papeis == null || this.papeis.isEmpty())
		{
			return null;
		}
		
		return this.papeis.stream()
				.map( 
					papel -> 
						papel.getGrantedAuthority()
				)
					.collect(Collectors.toSet()
		);
		
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonLocked()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isEnabled()
	{
		return this.isAtivo;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public String getPassword()
	{
		return this.senha;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public String getUsername()
	{
		return this.login;
	}
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	@Transient
	public int getIdadeAnos(){

		LocalDate dataAtual = LocalDate.now();

		final Period periodo = Period.between(this.getDataNascimento(), dataAtual);
		
		return periodo.getYears();
		
	}
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	@Transient
	public boolean getIsPersonalOrAdministrador(){
		
		if(papeis == null){
			return false;
		}
		
		return papeis.stream().anyMatch(papel -> Papel.PERSONAL.equals(papel) || Papel.ADMINISTRATOR.equals(papel));
		
	}
	
	/*-------------------------------------------------------------------
	 *						GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
}