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
import javax.persistence.Persistence;
import java.util.Collection;

@Controller
public class FirstController {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("SD2Registry");
	EntityManager em = emf.createEntityManager();

	@Autowired
	AccountRepository accountRepository;
	
	@RequestMapping("/")
	public String getIndex(){
		return "wishlist";
	}

	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;
	}


	@RequestMapping("/createAccount")
	public String getCreateAccount(){
		return "createAccount";
	}

	@RequestMapping("/REST/AccountTest")
	@ResponseBody
	public Account getAccountTest(){
		return em.find(Account.class, 1);
	}
	
//	@RequestMapping("/REST/NewAccount")
//	public String addNewAccount(String accountnameBox,
//								String passwordBox,
//								String emailBox,
//								String firstnameBox,
//								String lastnameBox,
//								String ageBox) {
//
//		//	Data validation goes here
//		createAccount(accountnameBox, passwordBox, emailBox, firstnameBox, lastnameBox, Integer.parseInt(ageBox));
//
//		return "success";
//	}



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
	    
		try {
			return accountRepository.saveAndFlush(futureAccount);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
