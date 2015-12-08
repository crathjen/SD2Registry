package com.sd2.Registry;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@ManyToMany(fetch=FetchType.EAGER )
	@JoinTable(name="item_wishlist", 
	joinColumns = @JoinColumn(name = "wishlist_id", referencedColumnName="id"),
	inverseJoinColumns= @JoinColumn(name = "item_id", referencedColumnName="id"))
	private List<Item> items;
	
	
	
	
	public List<Item_WishList> getZippers() {
		return zippers;
	}

	public void setZippers(List<Item_WishList> zippers) {
		this.zippers = zippers;
	}

	@OneToMany(mappedBy="wishList", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Item_WishList> zippers;

	public WishList() {
		//no-arg constructor function
	}
	
	//in order to use WishListRepository, the mutator methods must correspond exactly to their field's data type (i.e., they must use Integer and not just int).
	public Integer getId() {
		return id;
	}

	//in order to use WishListRepository, the mutator methods must correspond exactly to their field's data type (i.e., they must use Integer and not just int).
	public void setId(Integer id) {
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
