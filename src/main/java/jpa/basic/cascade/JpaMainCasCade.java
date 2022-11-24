package jpa.basic.cascade;

import jpa.basic.entity.Child;
import jpa.basic.entity.Member2;
import jpa.basic.entity.Parent;
import jpa.basic.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMainCasCade {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Child child1 = new Child("child1");     // N
            Child child2 = new Child("child2");

            Parent parent = new Parent("parent");    // 1
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            //em.persist(child1);
            //em.persist(child2);

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

