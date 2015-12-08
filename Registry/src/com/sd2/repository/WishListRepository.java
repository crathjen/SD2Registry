package com.sd2.repository;

import com.sd2.Registry.WishList;

public interface WishListRepository extends JPARepoWithRefresh<WishList, Integer>{

    //this is where you write Spring Data Queries to be read and interpreted by Spring
//    
//    List<Account> findAccountByAccountName(String accountname);
//    List<Account> findAccountByAccountNameLike(String accountname);
    
}

