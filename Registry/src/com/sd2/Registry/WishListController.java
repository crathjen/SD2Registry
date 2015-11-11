package com.sd2.Registry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sd2.Repository.WishListRepository;

@Controller
public class WishListController {
//	@Autowired
//	protected WishListRepository wishListRepository;
	
	@RequestMapping("/REST/wishLists")
	public List<WishList> getUserWishlist(){
//		if (wishListRepository != null){
//			List<WishList> wishLists = wishListRepository.findAll();
//			return wishLists;
//		}
//		else{
//		System.out.println("wishListRepository is null");	
//		}
		
		return null;
	}; 
	
}
