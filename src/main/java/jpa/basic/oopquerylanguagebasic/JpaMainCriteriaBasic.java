package jpa.basic.oopquerylanguagebasic;

import jpa.basic.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMainCriteriaBasic {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Member member = new Member();
            member.setUsername("hello");
            em.persist(member);

            em.flush();
            em.clear();

            // Criteria 사용준비
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = criteriaBuilder.createQuery(Member.class);

            // 루트 클래스(조회를 시작할 클래스)
            Root<Member> m = query.from(Member.class);

            // 쿼리생성
            CriteriaQuery<Member> cq = query.select(m).where(criteriaBuilder.equal(m.get("username"), "hello"));
            List<Member> resultList = em.createQuery(cq).getResultList();

            for (Member findMember : resultList) {
                System.out.println("member.getUsername() = " + findMember.getUsername());
            }

            tx.commit();    // 커밋
        }catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
