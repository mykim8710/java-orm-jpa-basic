package jpa.basic.valuetype;

import jpa.basic.entity.Address;
import jpa.basic.entity.Member5;
import jpa.basic.entity.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMainValueEmbeddedType {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            // embedded type mapping test
            Member5 member = new Member5();
            member.setHomeAddress(new Address("city", "street", "100"));
            member.setWorkAddress(new Address("work_city", "work_street", "work_100"));
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            member.setName("name");

            em.persist(member);

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
