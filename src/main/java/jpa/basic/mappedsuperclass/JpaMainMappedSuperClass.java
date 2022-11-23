package jpa.basic.mappedsuperclass;

import jpa.basic.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class JpaMainMappedSuperClass {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            Board board = new Board();
            board.setTitle("board title");
            board.setCreateBy("people");
            board.setCreatedDate(LocalDate.now());
            em.persist(board);

            Article article = new Article();
            article.setAuthor("article author");
            article.setCreateBy("people2");
            article.setCreatedDate(LocalDate.now());
            em.persist(article);
            
            em.flush();
            em.clear();

            Board findBoard = em.find(Board.class, board.getId());
            System.out.println("findBoard = " + findBoard);

            Article findArticle = em.find(Article.class, article.getId());
            System.out.println("findArticle = " + findArticle);

            tx.commit();    // 커밋
        } catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
