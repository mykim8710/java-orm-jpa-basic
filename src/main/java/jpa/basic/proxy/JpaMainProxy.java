package jpa.basic.proxy;

import jpa.basic.entity.Member2;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainProxy {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Member2 member = new Member2();
            member.setName("hello");
            em.persist(member);

            em.flush();
            em.clear();

            // em.find()
            //Member2 findMember = em.find(Member2.class, member.getId());
            //System.out.println("findMember.getId() = " + findMember.getId());
            //System.out.println("findMember.getName() = " + findMember.getName());

            // em.getReference()
            Member2 findMember = em.getReference(Member2.class, member.getId());
            System.out.println("findMember = " + findMember.getClass()); // 프록시 객체
            //System.out.println("findMember.getId() = " + findMember.getId());

            //em.detach(findMember); //em.close(), em.clear()  영속 -> 준영속, LazyInitializationException, could not initialize proxy [jpa.basic.entity.Member2#1] - no Session
            //System.out.println("findMember.getName() = " + findMember.getName()); // 이때 실제 db에 쿼리를 날려 실제 엔티티 객체를 생성

            // boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember); // • 프록시 인스턴스의 초기화 여부 확인 
            //System.out.println("loaded = " + loaded);

            // Hibernate.initialize(findMember); // 프록시 강제 초기화

            tx.commit();    // 커밋
        } catch (Exception e) {
            tx.rollback();  // 롤백
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
