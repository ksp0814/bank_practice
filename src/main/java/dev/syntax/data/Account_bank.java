package dev.syntax.data;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import dev.syntax.model.Account;

public class Account_bank {	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("bank");
	EntityManager manager = factory.createEntityManager();
	EntityTransaction transaction = manager.getTransaction();
	
	
	
	 @Transactional
	    public List<Account> findByMemberIdAndBankId(int memberId, int bankId) {
	        String jpql = "SELECT a FROM Account a WHERE a.bank.id = :bankId AND a.member.id = :memberId";
	        TypedQuery<Account> query = manager.createQuery(jpql, Account.class);
	        query.setParameter("bankId", bankId);
	        query.setParameter("memberId", memberId);
	        return query.getResultList();
	    }
	 
	 public void close() {
	        factory.close();  // EntityManagerFactory 자원 해제
	    }
}
