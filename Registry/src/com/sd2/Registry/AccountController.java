package com.sd2.Registry;

import com.sd2.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;

@Controller
public class AccountController {

	//create the EntityManager, used to access the database and perform CRUD operations with Accounts.
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("SD2Registry");
	EntityManager em = emf.createEntityManager();

	// autowire the AccountRepository in case we choose to switch to this method of retrieving
	// accounts later, as opposed to using the EntityManager like we are now.
	@Autowired
	AccountRepository accountRepository;
	
	@RequestMapping("/")
	public String getIndex(){
		return "wishlist";
	}

	// Spring Security uses this mapping to handle the Logging in. This configures the GET login page, so that if 
	// any errors are present or if an Account logs out it will render them in the div at the top of the login.jsp
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			// request both the error and logout URL parameters
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		
		// if the URL parameter error is present, give the model a thing
		// called error so we can render its message in the login.jsp
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		// if the URL parameter logout is present, give the model a thing called msg 
		// (containing the logout msg) so we can render its message in the login.jsp
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		model.setViewName("login");

		// having added any potential objects to the model, return it and render the jsp
		return model;
	}

	// maps to the createAccount page, accessed from the login.jsp, which is served if a user is not authenticated.
	@RequestMapping("/createAccount")
	public String getCreateAccount(){
		return "createAccount";
	}

	// used initially to test that we could pull an account out of the database.
	@RequestMapping("/REST/AccountTest")
	@ResponseBody
	public Account getAccountTest(){
		return em.find(Account.class, 1);
	}
	
	// creates a new account using EntityManager and EntityTransaction.
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
	    
	    return futureAccount;
	}

	// returns all of the accounts in the sd2registry database.
	public Collection<Account> findAllAccounts() {
	     return em.createQuery("SELECT a FROM Account a ORDER BY a.accountName", Account.class).getResultList();
	}
	
	// returns a single account using a provided accountName.
	public Account findAccountByAccountName(String accountName) {
	    return (Account) em.createQuery("SELECT a FROM Account a WHERE a.accountName = :accountName").setParameter("accountName", accountName).getSingleResult();
	}
	
	// returns a single account using a provided accountId
	public Account findAccountById(int id) {
	    return (Account) em.createQuery("SELECT a FROM Account a WHERE a.id = :id").setParameter("id", id).getSingleResult();
	}

}
