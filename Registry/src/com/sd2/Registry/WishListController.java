package com.sd2.Registry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sd2.repository.AccountRepository;
import com.sd2.repository.Item_WishListRepository;
import com.sd2.repository.WishListRepository;

@Controller
public class WishListController {
	
//	EntityManagerFactory emf = Persistence.createEntityManagerFactory("SD2Registry");
//	EntityManager em = emf.createEntityManager();
	
	@Autowired
	protected WishListRepository wishListRepository;
	
	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	protected Item_WishListRepository item_WishListRepository;
	
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
	
	@RequestMapping(value="/REST/wishLists/delete", consumes="application/json")
	@ResponseBody
	public int deleteUserWishlist(@RequestBody WishList listToDelete) {
		//return 1 for success, -1 for failure
		if (listToDelete == null)
		{
			return -1;  //indicates error
		}
		
		List<Item_WishList> item_wishListsToDelete = item_WishListRepository.findItem_WishListByWishlistId(listToDelete.getId());
		item_WishListRepository.delete(item_wishListsToDelete);
		wishListRepository.delete(listToDelete.getId());
		return 1;
	}; 
	
}
