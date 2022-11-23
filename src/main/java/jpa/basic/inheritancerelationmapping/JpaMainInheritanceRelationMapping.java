package jpa.basic.inheritancerelationmapping;

import jpa.basic.entity.Album;
import jpa.basic.entity.Book;
import jpa.basic.entity.Item;
import jpa.basic.entity.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainInheritanceRelationMapping {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // EntityManagerFactory app 로딩시점에 딱 하나만 만든다.

        EntityManager em = emf.createEntityManager();
        // 트랜젝션 단위(db 커넥션을 얻어서 쿼리를 날리고 결과를 얻는)로 EntityManager를 만들어야줘야 한다.
        // 쉽게 생각하면 db 커넥션을 하나 얻은 것이다.

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜젝션을 시작

        try {
            // movie insert
            Movie movie = new Movie();
            movie.setActor("actor1");
            movie.setDirector("director1");
            movie.setName("name~");
            movie.setPrice(10000);
            em.persist(movie);

            // Album insert
            Album album = new Album();
            album.setName("album name");
            album.setPrice(20000);
            album.setArtist("album artist");
            em.persist(album);

            // book insert
            Book book = new Book();
            book.setIsbn("isbn");
            book.setAuthor("book author");
            book.setPrice(222);
            book.setName("book name");
            em.persist(book);

            em.flush();
            em.clear();

            // movie 조회
            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

            // album 조회
            Album findAlbum = em.find(Album.class, album.getId());
            System.out.println("findAlbum = " + findAlbum);

            // book 조회
            Book findBook = em.find(Book.class, book.getId());
            System.out.println("findBook = " + findBook);


            // 구현 클래스마다 테이블 전략 시, 자식객체의 id로 부모 객체 조회 시 문제점
            // 전체 테이블을 다뒤져봐야하므로 복잡한 쿼리가 나가며 비효율적으로 동작
            Item findItem = em.find(Item.class, book.getId());
            System.out.println("findItem = " + findItem);

            tx.commit();    // 커밋
        } catch (Exception e) {
            tx.rollback();  // 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
