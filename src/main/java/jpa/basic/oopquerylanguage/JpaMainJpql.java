package jpa.basic.oopquerylanguage;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMainJpql {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            // jpql
            Member member = new Member();
            member.setUsername("hello");
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> findMemberList = em.createQuery(
                    "select m from Member m where m.username like '%he%'", // Entity 체를 대상으로 조회
                    Member.class).getResultList();
            for (Member findMember : findMemberList) {
                System.out.println("findMember.getUsername() = " + findMember.getUsername());
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
        member.setUsername(name);
        return member;
    }
}
