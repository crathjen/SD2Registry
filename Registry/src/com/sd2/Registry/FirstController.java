package com.sd2.Registry;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("SD2Registry");
	EntityManager em = emf.createEntityManager();
	
	@RequestMapping("/")
	public String getIndex(){
		return "index";
	}
	
	@RequestMapping("/REST/AccountTest")
	@ResponseBody
	public Account getAccountTest(){
		return em.find(Account.class, 1);
	}
	
	@RequestMapping("/REST/NewAccount")
	public String addNewAccount(String accountnameBox,
								String passwordBox,
								String emailBox,
								String firstnameBox,
								String lastnameBox,
								String ageBox) {
		
		//	Data validation goes here
		createAccount(accountnameBox, passwordBox, emailBox, firstnameBox, lastnameBox, Integer.parseInt(ageBox));
		
		return "success";
	}



	@Transactional
	public Account createAccount(String accountname, String password, String email, String firstName, String lastName, int age) {
	    em.clear();
	    Account futureAccount = new Account();
	    
	    futureAccount.setAccountName(accountname);
	    futureAccount.setPassword(password);
	    futureAccount.setEmail(email);
	    futureAccount.setFirstName(firstName);
	    futureAccount.setLastName(lastName);
	    futureAccount.setAge(age);
	    
	    EntityTransaction et = em.getTransaction();

		et.begin();		
			em.persist(futureAccount);
			et.commit();
			System.out.println("Added new account to database.");
	    
	    return futureAccount;
	}

	public Collection<Account> findAllAccounts() {
	     return em.createQuery("SELECT a FROM Account a ORDER BY a.accountName", Account.class).getResultList();
	}
	
	public Account findAccountByAccountName(String accountName) {
	    return (Account) em.createQuery("SELECT a FROM Account a WHERE a.accountName = :accountName")
	                        .setParameter("accountName", accountName).getSingleResult();
	}
	
	public Account findAccountById(int id) {
	    return (Account) em.createQuery("SELECT a FROM Account a WHERE a.id = :id")
	                        .setParameter("id", id).getSingleResult();
	}

}
