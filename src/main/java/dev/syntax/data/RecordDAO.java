package dev.syntax.data;

import dev.syntax.model.Record;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class RecordDAO {

    private static EntityManagerFactory factory;

    // 스태틱 초기화 블록을 사용하여 EntityManagerFactory를 애플리케이션 전역에서 한 번만 초기화
    static {
        factory = Persistence.createEntityManagerFactory("bank");
    }

    public List<Record> findRecordsByAccountId(int accountId) {
        EntityManager manager = factory.createEntityManager();
        List<Record> records = null;

        try {
            String jpql = "SELECT r from Record r WHERE r.account.id = :accountId";
            TypedQuery<Record> query = manager.createQuery(jpql, Record.class);
            query.setParameter("accountId", accountId);
            records = query.getResultList();
        } finally {
            manager.close();  // 작업이 끝난 후 EntityManager 자원 해제
        }

        return records;
    }

    public void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();  // EntityManagerFactory 자원 해제
        }
    }
}
