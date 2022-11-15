package jpa.basic.ex2;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 영속성 컨텍스트의 이점
 * 변경 감지(Dirty Checking) : 엔티티 수정
 */

public class JpaMainDirtyChecking {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.
        EntityManager em = emf.createEntityManager();// 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다., 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Member findMember = em.find(Member.class, 200L);    // select sql
            findMember.setUsername("A_수정");

            //em.update(member) 이런 코드가 있어야 하지 않을까?
            // jpa는 값을 바꾸면 트랜잭션의 커밋시점에 변경을 반영

            System.out.println("===================");

            tx.commit();    // 커밋과 동시에 db에 sql 쿼리가 날라간다.
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
