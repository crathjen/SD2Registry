package com.sd2.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface JPARepoWithRefresh <T, ID extends Serializable> extends JpaRepository<T, ID> {
	
	public void emClear();
	
	public T emMerge(T obj);
	
	@Override
	@Transactional()
	@Modifying(clearAutomatically=true)
	public <S extends T> S saveAndFlush(S arg0);
	
	public void emRefresh(T obj);
}
