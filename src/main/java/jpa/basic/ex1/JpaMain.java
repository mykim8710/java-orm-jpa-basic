package jpa.basic.ex1;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            // 비지니스 로직 : 회원 저장
//            Member member = createMember(1L, "memberA");
//            em.persist(member);

            // 비지니스 로직 : 단건 조회
//            Member findMember = em.find(Member.class, 1L); // id 1L의 회원 조회
//            System.out.println("findMember id = " + findMember.getId());
//            System.out.println("findMember name = " + findMember.getName());

            // 비지니스 로직 : 회원삭제
//            Member findMember = em.find(Member.class, 1L); // id 1L의 회원 조회
//            em.remove(findMember);

            // 비지니스 로직 : 회원수정
//            Member findMember = em.find(Member.class, 1L); // id 1L의 회원 조회
//            findMember.setName("memberJPA");


            // JPQL로 전체 회원 검색
            List<Member> findMembers = em.createQuery("select m from Member AS m", Member.class)
                                            .setFirstResult(1).setMaxResults(2) // pagination : persistence.xml에 설정된 sql 종류 따라 자동으로 쿼리가 결정
                                            .getResultList();

            for (Member findMember : findMembers) {
                System.out.println("findMember.getName() = " + findMember.getName());
            }

            tx.commit();    // 커밋
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }

    private static Member createMember(Long id, String name) {
        Member member = new Member();
        member.setId(id);
        member.setName(name);
        return member;
    }
}
