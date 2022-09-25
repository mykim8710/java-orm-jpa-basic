package jpa.basic.ex2;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainPersistence {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.
        EntityManager em = emf.createEntityManager();// 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다., 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("name100");  // Member 엔티티가 비영속상태

            // 영속
            System.out.println("===== BEFORE =====");
            em.persist(member);      // Member 엔티티가 영속상태 : 엔티티 매니저 안에 영속성컨텍스트에서 엔티티를 관리
            // em.detach(member);    // member 엔티티를 영속성 컨텍스트에서 분리 : 준영속 상태
            // em.remove(member);    // 객체를 삭제한 상태 : 삭제

            System.out.println("===== AFTER =====");
            // 영속상태가 된다해서 바로 바로 db에 쿼리가 날라가지 않는다.

            tx.commit();    // 커밋 : 트랜잭션을 커밋하는 시점에 영속성 컨택스트에서 db에 쿼리를 날린다.
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
