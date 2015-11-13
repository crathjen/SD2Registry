package com.sd2.Registry;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sd2.repository.AccountRepository;
import com.sd2.repository.WishListRepository;

@Controller
public class WishListController {
	
	@Autowired
	protected WishListRepository wishListRepository;
	
	@Autowired
	protected AccountRepository accountRepository;

	//used in DEPRECATED method needed to delete the Item to Wishlist relationship stored in item_wishlist table
//	@Autowired
//	protected Item_WishListRepository item_WishListRepository;
	
	// the recipient of an AJAX call from the wishlist.jsp $(document).ready() 
	// to populate it's table with all the wishlists from the database.
	@RequestMapping("/REST/wishLists")
	@ResponseBody
	public List<WishList> getUserWishlist(){
		
		if (wishListRepository != null) {
			List<WishList> wishLists = wishListRepository.findAll();
			return wishLists;
		} else{
			System.out.println("wishListRepository is null");	
			return null;
		}
	}; 
	
	// this method is the recipient of the WishList ViewModel's saveWishList method, 
	// an AJAX call to BOTH save and update a WishList in the database.
	@RequestMapping(value="/REST/wishLists/save", consumes="application/json")
	@ResponseBody
	public int editUserWishlist(
			@RequestBody WishList listEdited, 
			//use java.security.Principal to get the active Account's accountName
			Principal principal) {

		if (listEdited == null) {
			return -1;  //indicates error, the WishList we sent to Java via AJAX failed.
		}
		
		//lookup the Account using the accountName we got out of the java.security.Principal to set the WishList's owning Account
		listEdited.setAccount(accountRepository.findAccountByAccountName(principal.getName()).get(0));
		return wishListRepository.save(listEdited).getId();
		
	}; 
	
	// this method is the recipient of the WishList ViewModel's deleteWishList method, 
	// an AJAX call to delete a WishList in the database. Returns 1 for successful delete, -1 for error.
	@RequestMapping(value="/REST/wishLists/delete", consumes="application/json")
	@ResponseBody
	public int deleteUserWishlist(@RequestBody WishList listToDelete) {
		//return 1 for success, -1 for failure
		if (listToDelete == null) {
			return -1;  //indicates error, the WishList we sent to Java via AJAX failed.
		}
		
			//DEPRECATED, originally needed to delete the Item to Wishlist relationship (stored in many to many table item_wishlist) MANUALLY.
			// Instead, we are now using CascadeType.REMOVE on the item_wishlists, mapped in the Wishlist class,
			// which will CASCADE and delete those relationships when we delete a Wishlist.
		//List<Item_WishList> item_wishListsToDelete = item_WishListRepository.findItem_WishListByWishlistId(listToDelete.getId());
		//item_WishListRepository.delete(item_wishListsToDelete);

		//get the WishList's id and use it to delete the WishList.
		wishListRepository.delete(listToDelete.getId());
		return 1;
	}; 
	
}
