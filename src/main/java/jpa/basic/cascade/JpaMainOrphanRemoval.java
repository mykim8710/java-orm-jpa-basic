package jpa.basic.cascade;

import jpa.basic.entity.Child;
import jpa.basic.entity.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainOrphanRemoval {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            //  orphanRemoval = true
//            Child child1 = new Child("child1");     // N
//            Child child2 = new Child("child2");
//
//            Parent parent = new Parent("parent");    // 1
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);
//
//            em.flush();
//            em.clear();
//
//            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildList().remove(0); // 자식 엔티티를 컬렉션에서 제거


            // cascade = CascadeType.ALL + orphanRemoval = true
            // 두 옵션을 모두 활성화 하면 부모 엔티티를 통해서 자식의 생명 주기를 관리할 수 있음
            Child child1 = new Child("child1");     // N
            Child child2 = new Child("child2");

            Parent parent = new Parent("parent");    // 1
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            em.remove(findParent);

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

