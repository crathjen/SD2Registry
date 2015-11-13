package com.sd2.Registry;

import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private Double price;
	
	private String vendor;
	
	//direct many to many mapping, where we are reaching through the item_wishlist table to get the corresponding wishlists.
	@ManyToMany(mappedBy="items", cascade = CascadeType.ALL)
	private Collection<WishList> wishlists;
	
	//the many to many table itself.
	@OneToMany(mappedBy="item")
	private List<Item_WishList> zippers;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;
	
	private String description;

	public Item() {
		//no arg constructor function
	}
	
	//in order to use ItemRepository, the mutator methods must correspond exactly to their field's data type (i.e., they must use Integer and not just int).
	public Integer getId() {
		return id;
	}
	
	//in order to use ItemRepository, the mutator methods must correspond exactly to their field's data type (i.e., they must use Integer and not just int).
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
