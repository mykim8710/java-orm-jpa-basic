package jpa.basic.valuetype;

import jpa.basic.entity.Address;
import jpa.basic.entity.Member5;
import jpa.basic.entity.Member6;
import jpa.basic.entity.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMainValueTypeCollection {
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
            Member6 member = new Member6();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "homeStreet", "300"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new Address("oldCity1", "oldStreet1", "100"));
            member.getAddressHistory().add(new Address("oldCity2", "oldStreet2", "200"));

            em.persist(member); // 값타입 컬렉션들은 따로 persist 하지않음, member만 persist

            em.flush();
            em.clear();

//            // 값타입 컬렉션 조회
//            System.out.println("=========== START ===========");
//            Member6 findMember = em.find(Member6.class, member.getId());
//
//            System.out.println("=========== COLLECTION addressHistory 조회(지연로딩) ===========");
//            List<Address> addressHistory = findMember.getAddressHistory(); // 이 시점에 sql 쿼리가 날라간다.
//            for (Address address : addressHistory) {
//                System.out.println("address = " + address.getCity());
//            }
//
//            System.out.println("=========== COLLECTION favoriteFoods 조회(지연로딩) ===========");
//            Set<String> favoriteFoods = findMember.getFavoriteFoods(); // 이 시점에 sql 쿼리가 날라간다.
//            System.out.println("favoriteFoods = " + favoriteFoods);

            // 값타입 컬렉션 수정
            Member6 findMember = em.find(Member6.class, member.getId());

            // 값타입 HomeAddress update
            // homeCity -> newCity
            //findMember.getHomeAddress().setCity("New City"); // X
//            System.out.println("=========== 값타입 HomeAddress update ===========");
//            findMember.setHomeAddress(new Address("newCity", "newStreet", "300")); // 값타입은 객체 전체를 갈아끼워야한다.

            // 값타입 컬렉션, Set<String> update
            // 치킨 -> 쌀국수 , 컬렉션의 값만 변경해도 알아서 쿼리가 날라간다.
//            System.out.println("=========== 값타입 컬렉션, Set<String> update ===========");
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("쌀국수");

            // 값타입 컬렉션, List<Address> update
            // old1 -> new1
            // remove, new Address("oldCity1", "oldStreet1", "100")와 동일한 것을 찾아서 삭제해준다. equals 메서드를 정확하게 구현해두어야 한다.
            System.out.println("=========== 값타입 컬렉션, List<Address> update ===========");
            findMember.getAddressHistory().remove(new Address("oldCity1", "oldStreet1", "100"));
            findMember.getAddressHistory().add(new Address("newCity1", "newStreet1", "100"));

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

