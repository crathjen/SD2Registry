package com.sd2.Registry;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WishListController {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("SD2Registry");
	EntityManager em = emf.createEntityManager();
	
//	@Autowired
//	protected WishListRepository wishListRepository;
	
	@RequestMapping("/REST/wishLists")
	@ResponseBody
	public List<WishList> getUserWishlist(){
//		if (wishListRepository != null){
//			List<WishList> wishLists = wishListRepository.findAll();
//			return wishLists;
//		}
//		else{
//		System.out.println("wishListRepository is null");	
//		}
		
		return em.createQuery("SELECT w from WishList w", WishList.class).getResultList();
		
		
	}; 
	
}
