package dev.syntax.data;

import dev.syntax.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.mysql.cj.Query;

public class MemberDAO {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("bank");
	EntityManager manager = factory.createEntityManager();
	EntityTransaction transaction = manager.getTransaction();
	
	 @Transactional
	    public Member findByLoginIdAndPassword(String loginId, String password) {
		 String jpql = "SELECT m FROM Member m WHERE m.loginId = :loginId AND m.password = :password";
		 TypedQuery<Member> query = manager.createQuery(jpql, Member.class);
		 query.setParameter("loginId", loginId);
		 query.setParameter("password", password);
		 
		 try {
	            return query.getSingleResult();
	        } catch (Exception e) {
	            return null; // No result or multiple results
	        }
	    }

	    public void close() {
	        if (manager != null) {
	            manager.close();
	        }
	        if (factory != null) {
	            factory.close();
	        }
	    }

	
    
    
}