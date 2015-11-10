package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sd2.Registry.Account;

public class Test {

	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SD2Registry");
		EntityManager em = emf.createEntityManager();
		
		System.out.println(em.find(Account.class,1).getAccountName());
	}

}
