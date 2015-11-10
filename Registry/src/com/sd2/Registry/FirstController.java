package com.sd2.Registry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
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
	
	
}
