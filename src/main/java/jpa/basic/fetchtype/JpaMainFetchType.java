package jpa.basic.fetchtype;

import jpa.basic.entity.Member2;
import jpa.basic.entity.Team;

import javax.persistence.*;
import java.util.List;

public class JpaMainFetchType {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Team team = new Team();
            team.setName("Team A");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("Team A");
            em.persist(team2);

            Team team3 = new Team();
            team3.setName("Team A");
            em.persist(team3);

            Member2 member = new Member2();
            member.setName("hello");
            member.setTeam(team);
            em.persist(member);

            Member2 member2 = new Member2();
            member2.setName("hello");
            member2.setTeam(team2);
            em.persist(member2);

            Member2 member3 = new Member2();
            member3.setName("hello");
            member3.setTeam(team3);
            em.persist(member3);

            Member2 member4 = new Member2();
            member4.setName("hello");
            member4.setTeam(team2);
            em.persist(member4);

            Member2 member5 = new Member2();
            member5.setName("hello");
            member5.setTeam(team);
            em.persist(member5);

            em.flush();
            em.clear();

//            Member2 m = em.find(Member2.class, member.getId());
//            System.out.println("m = " +m.getTeam().getClass());
//
//            System.out.println("=====================");
//            m.getTeam().getName(); // 이때 db에 쿼리가 나감, Team 프록시 객체 초기화 실제 Team 엔티티 객체에 조회
//            System.out.println("=====================");

            // 즉시로딩 -> N + 1 문제
            List<Member2> members = em.createQuery("select m from Member2 m", Member2.class).getResultList();
            // jpql -> sql로 번역
            // Member를 조회, Team이 즉시로딩이면 다시 Team조회를 위한 sql이 날라감
            // select * from Member;
            // select * from Team where team_id = member.~~~
            // member의 team에 따라 team 조회 쿼리가 여러번 나감

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
