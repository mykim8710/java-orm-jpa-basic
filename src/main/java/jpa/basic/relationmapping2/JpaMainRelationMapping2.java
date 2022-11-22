package jpa.basic.relationmapping2;

import jpa.basic.entity.Member2;
import jpa.basic.entity.Member3;
import jpa.basic.entity.Team;
import jpa.basic.entity.Team2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMainRelationMapping2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            // 연관관계 매핑 : 일대다 예
            Member3 member = new Member3();
            member.setName("member1");

            em.persist(member);

            Team2 team = new Team2();
            team.setName("Team 1");

            //
            team.getMembers().add(member);
            em.persist(team);


            tx.commit();    // 커밋
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
