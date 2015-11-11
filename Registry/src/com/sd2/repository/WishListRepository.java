package com.sd2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sd2.Registry.WishList;

public interface WishListRepository extends JpaRepository<WishList, Integer>{

    //this is where you write Spring Data Queries to be read and interpreted by Spring
//    
//    List<Account> findAccountByAccountName(String accountname);
//    List<Account> findAccountByAccountNameLike(String accountname);
    
}

