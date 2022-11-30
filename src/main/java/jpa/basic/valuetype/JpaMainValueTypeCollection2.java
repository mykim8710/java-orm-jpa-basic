package jpa.basic.valuetype;

import jpa.basic.entity.Address;
import jpa.basic.entity.Address2;
import jpa.basic.entity.AddressEntity;
import jpa.basic.entity.Member7;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainValueTypeCollection2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            // 값 타입 컬렉션 저장
            Member7 member = new Member7();
            member.setName("member1");
            member.setHomeAddress(new Address2("homeCity", "homeStreet", "300"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new AddressEntity("oldCity1", "oldStreet1", "100"));
            member.getAddressHistory().add(new AddressEntity("oldCity2", "oldStreet2", "200"));

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

