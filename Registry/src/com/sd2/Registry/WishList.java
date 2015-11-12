package com.sd2.Registry;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "wishlist")
public class WishList {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="item_wishlist", 
	joinColumns = @JoinColumn(name = "item_id", referencedColumnName="id"),
	inverseJoinColumns= @JoinColumn(name = "wishlist_id", referencedColumnName="id"))
	private List<Item> items;
	
	
	@OneToMany(mappedBy="wishList", cascade=CascadeType.REMOVE)
	private List<Item_WishList> zippers;

	public WishList() {
		//no-arg constructor function
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
