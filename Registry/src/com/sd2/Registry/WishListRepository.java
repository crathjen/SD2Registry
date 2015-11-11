package com.sd2.Registry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Integer>{

    //this is where you write Spring Data Queries to be read and interpreted by Spring
//    
//    List<Account> findAccountByAccountName(String accountname);
//    List<Account> findAccountByAccountNameLike(String accountname);
    
}

