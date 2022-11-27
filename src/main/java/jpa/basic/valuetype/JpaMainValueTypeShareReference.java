package jpa.basic.valuetype;

import jpa.basic.entity.Address;
import jpa.basic.entity.Member5;
import jpa.basic.entity.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMainValueTypeShareReference {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Address address = new Address("city1", "street1", "100");
            Member5 member1 = new Member5();
            member1.setName("member 1");
            member1.setHomeAddress(address);
            em.persist(member1);

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipCode()); // 복사해서 사용해야한다.
            Member5 member2 = new Member5();
            member2.setName("member 2");
            member2.setHomeAddress(copyAddress);
            em.persist(member2);

            //member1.getHomeAddress().setCity("new City");

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
