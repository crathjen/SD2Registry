package com.sd2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sd2.Registry.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
