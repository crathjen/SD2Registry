package com.sd2.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sd2.Registry.Account;

public class UserService implements UserDetailsService{

	@PersistenceContext(unitName="entityManagerFactory")
	protected EntityManager em;
	
//	@Autowired
//	AccountRepository ar;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return em.createQuery("select a from Account a where a.accountName = ?1", Account.class).setParameter(1, username).getResultList().get(0);
//		return ar.findAccountByAccountName(username).get(0);
	}

}
