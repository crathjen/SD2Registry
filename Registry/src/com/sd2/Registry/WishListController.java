package com.sd2.Registry;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sd2.repository.AccountRepository;
import com.sd2.repository.WishListRepository;

@Controller
public class WishListController {
	
//	EntityManagerFactory emf = Persistence.createEntityManagerFactory("SD2Registry");
//	EntityManager em = emf.createEntityManager();
	
	@Autowired
	protected WishListRepository wishListRepository;
	
	@Autowired
	protected AccountRepository accountRepository;
	
	@RequestMapping("/REST/wishLists")
	@ResponseBody
	public List<WishList> getUserWishlist(){
		if (wishListRepository != null){
			List<WishList> wishLists = wishListRepository.findAll();
			return wishLists;
		}
		else{
		System.out.println("wishListRepository is null");	
		}
		
		//return em.createQuery("SELECT w from WishList w", WishList.class).getResultList();
		return null;
		
	}; 
	
	@RequestMapping(value="/REST/wishLists/save", consumes="application/json")
	@ResponseBody
	public int editUserWishlist(@RequestBody WishList listEdited) {

		if (listEdited == null)
		{
			return -1;  //indicates error
		}
		Account account = accountRepository.getOne(1);
		listEdited.setAccount(account);
		return wishListRepository.save(listEdited).getId();
		
	}; 
	
}
