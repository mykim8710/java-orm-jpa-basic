package jpa.basic.relationmapping;

import jpa.basic.entity.Member2;
import jpa.basic.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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
//            // Team 저장
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            // Member 저장
//            Member2 member = new Member2();
//            member.setName("user 1");
//            //member.setTeamId(team.getId());
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush(); // 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
//            em.clear();

//             [Member 조회]
//            Member2 findMember = em.find(Member2.class, member.getId());
//            System.out.println("findMember id = " + findMember.getId());
//            System.out.println("findMember name = " + findMember.getName());

//             [Team 조회]
//             연관관계가 없음
//             Long findTeamId = findMember.getTeamId();
//             Team findTeam = em.find(Team.class, findTeamId);

            // 연관관계 있음 : 참조를 사용해서 연관관계 조회
//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam id = " + findTeam.getId());
//            System.out.println("findTeam name = " + findTeam.getName());
//
//            List<Member2> members = findTeam.getMembers();
//            for (Member2 member2 : members) {
//                System.out.println("member's name in Team = " + member2.getName());
//            }


            // 양방향 매핑 시 가장 많이 하는 실수(연관관계의 주인에 값을 입력하지 않음)
//            Member2 member = new Member2();
//            member.setName("member 1");
//            em.persist(member);
//
//            Team team = new Team();
//            team.setName("TEAM_B");
//            team.getMembers().add(member);
//            em.persist(team);


            // 양방향 매핑 시 가장 많이 하는 실수 : 수정(양방향 매핑시 연관관계의 주인에 값을 입력해야 한다.)
            Team team = new Team();
            team.setName("TEAM_B");
            em.persist(team);

            Member2 member = new Member2();
            member.setName("member 1");

            member.changeTeam(team); // ** 연관관계 편의 메서드

            em.persist(member);

            //team.getMembers().add(member); // ** 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자,

//            em.flush(); // 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member2> members = findTeam.getMembers();

            System.out.println("===================");
            System.out.println("members size = " +members.size());
            for (Member2 member2 : members) {
                System.out.println("member2.getName() = " + member2.getName());
            }
            System.out.println("===================");

            tx.commit();    // 커밋
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
