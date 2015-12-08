package com.sd2.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class RepositoryRefreshImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID> implements JPARepoWithRefresh<T,ID> {
	  private final EntityManager entityManager;

	  public RepositoryRefreshImpl(JpaEntityInformation entityInformation,
	                          EntityManager entityManager) {
	    super(entityInformation, entityManager);


	    this.entityManager = entityManager;
	  }

	
	  
	@Transactional
	@Override
	public void emRefresh(T obj) {
		entityManager.refresh(obj);
		
	}

@Override
@Transactional
@Modifying(clearAutomatically=true)
public <S extends T> S saveAndFlush(S arg0) {
	return super.saveAndFlush(arg0);
};

	@Override
	public void emClear() {
		entityManager.clear();
		
	}



	@Override
	public T emMerge(T obj) {
		return entityManager.merge(obj);
	}
}
