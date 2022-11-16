package jpa.basic.relationmapping;

import jpa.basic.entity.Member2;
import jpa.basic.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainRelationMapping {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            //회원 저장
            Member2 member = new Member2();
            member.setName("user 1");
            member.setTeamId(team.getId());
            em.persist(member);


            // Member 조회
            Member2 findMember = em.find(Member2.class, member.getId());
            System.out.println("findMember = " + findMember);

            // Team 조회 : 연관관계가 없음
            Long findTeamId = findMember.getTeamId();
            Team findTeam = em.find(Team.class, findTeamId);

            System.out.println("findTeam = " + findTeam);

            tx.commit();    // 커밋
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
