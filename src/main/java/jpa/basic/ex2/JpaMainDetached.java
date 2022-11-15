package jpa.basic.ex2;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 준영속
 */

public class JpaMainDetached {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.
        EntityManager em = emf.createEntityManager();// 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다., 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Member findMember = em.find(Member.class, 201L);    // 영속
            findMember.setUsername("B_수정"); // dirty checking : flush

            em.detach(findMember);  // 준영속 상태, jpa가 관리하지 않음
            em.clear(); // 영속성 컨택스트(1차캐시)를 통으로 초기화
            em.close(); // 영속성 컨택스트 close

            System.out.println("======================");
            tx.commit();    // 커밋 : 준영속 상태임으로 아무일도 일어나지 않는다.
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
