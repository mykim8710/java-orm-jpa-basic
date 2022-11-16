package jpa.basic.columnmapping;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainColumnMapping {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Member member = new Member();
            member.setUsername("abc");

            System.out.println("========== BEFORE =========");
            em.persist(member); // @GeneratedValue(strategy = GenerationType.IDENTITY) 일때는 commit 시점이 아닌 이 시점에 db에 sql 쿼리를 날린다.
            System.out.println("member.getId() = " + member.getId());
            System.out.println("========== AFTER =========");

            tx.commit();    // 커밋
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
