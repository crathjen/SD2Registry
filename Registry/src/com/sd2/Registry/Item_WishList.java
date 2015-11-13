package com.sd2.Registry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_wishlist")
public class Item_WishList {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	// Although the table item_wishlist contains these columns, we have chosen to map them
	// with JPA to their actual POJOs (Plain old java objects).
//	@Column(name="wishlist_id")
//	private int wishlistId;
//	
//	@Column(name="item_id")
//	private int itemId;
	
	@ManyToOne
	@JoinColumn(name="item_id", nullable=false)
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="wishlist_id", nullable=false)
	private WishList wishList;
	
	private int priority;
	
	private Boolean purchased;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//DEPRECATED methods that we were using when we mapped to the Item_WishList's database columns instead of POJOs. See corresponding above comment.
//	public int getWishlistId() {
//		return wishlistId;
//	}
//
//	public void setWishlistId(int wishlistId) {
//		this.wishlistId = wishlistId;
//	}
//
//	public int getItemId() {
//		return itemId;
//	}
//
//	public void setItemId(int itemId) {
//		this.itemId = itemId;
//	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Boolean getPurchased() {
		return purchased;
	}

	public void setPurchased(Boolean purchased) {
		this.purchased = purchased;
	}
	
}
