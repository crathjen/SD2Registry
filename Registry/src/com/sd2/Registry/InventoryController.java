package com.sd2.Registry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sd2.repository.ItemRepository;
import com.sd2.repository.WishListRepository;

@RestController
public class InventoryController {
	@Autowired
	protected ItemRepository itemRepository;
	//method to map to Ajax url and return items
	@RequestMapping("/REST/items")
	public List<Item> getInventoryList() {
		return itemRepository.findAll();
	}
}
