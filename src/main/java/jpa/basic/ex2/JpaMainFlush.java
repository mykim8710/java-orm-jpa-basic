package jpa.basic.ex2;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 플러쉬
 */

public class JpaMainFlush {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.
        EntityManager em = emf.createEntityManager();// 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다., 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Member member = new Member(500L, "A");
            em.persist(member);

            em.flush(); // db에 쿼리가 바로 날라간다, 쓰기 지연 SQL 저장소에 있던 쿼리들이 db에 반영

            System.out.println("======================");
            tx.commit();    // 커밋과 동시에 db에 sql 쿼리가 날라간다.
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
