package br.com.eits.boot.domain.repository.account.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import br.com.eits.boot.domain.entity.account.Pessoa;

/**
 *
 */
public class IPessoaRepositoryImpl implements UserDetailsService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	private final EntityManager entityManager;

	@Autowired
	public IPessoaRepositoryImpl( EntityManager entityManager )
	{
		this.entityManager = entityManager;
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException
	{
		System.out.println("email: "+email);
		try
		{
			final String hql = "FROM Pessoa pessoa "
							+ "WHERE lower(pessoa.email) = lower(:email)";
			
			final TypedQuery<Pessoa> query = this.entityManager.createQuery( hql, Pessoa.class );
			query.setParameter( "email", email);
			
			return query.getSingleResult();
		}
		catch (NoResultException e)
		{
			throw new UsernameNotFoundException("Este email '"+email+"' é inválido");
		}
	}
}
