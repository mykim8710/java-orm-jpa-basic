## Java ORM JPA basic Project
### JPA란?
- JPA
    - Java Persistence API
    - 자바 진영의 ORM(Object Relational Mapping) 기술 표준
- *ORM?*
    - Object-relational mapping(객체 관계 매핑)
    - 객체는 객체대로 설계
    - 관계형 데이터베이스는 관계형 데이터베이스대로 설계
    - ORM 프레임워크가 중간에서 매핑
    - 대중적인 언어에는 대부분 ORM 기술이 존재
- JPA는 애플리케이션과 JDBC 사이에서 동작

### 프로젝트 설정
- java 11
- maven project
- name : hello-jpa
- ****groupId: jpa.basic.ex1****
- ****artifactId: hello****
- ****version: 1.0.0****

### 내용정리 
- persistence.xml
  - JPA 설정 파일
  - `**/META-INF/persistence.xml**` 위치
  - persistence-unit name으로 이름 지정
  - javax.persistence로 시작: JPA 표준 속성
  - hibernate로 시작: 하이버네이트 전용 속성
  
- **데이터베이스 방언**
  - **JPA는 특정 데이터베이스에 종속 X**
  - 각각의 데이터베이스가 제공하는 SQL 문법과 함수는 조금씩 다름
      - 가변 문자: MySQL은 VARCHAR, Oracle은 VARCHAR2
      - 문자열을 자르는 함수: SQL 표준은 SUBSTRING(), Oracle은 SUBSTR()
      - 페이징: MySQL은 LIMIT, Oracle은 ROWNUM
  - 방언 : SQL 표준을 지키지 않는 **특정 데이터베이스만의 고유한 기능**

- Jpa 기본 코드구조
```
[JpaMain Class]
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
            // 비지니스 로직 
            .......(CRUD)

            tx.commit();    // 성공시 커밋
        }catch (Exception e) {
            tx.rollback();  // 실패 시롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
```

- 주의점!
  - **엔티티 매니저 팩토리(emf)**는 하나만 생성해서 애플리케이션 전체에서 공유
  - **엔티티 매니저(em)**는 쓰레드간에 공유X (사용하고 버려야 한다).
  - **JPA의 모든 데이터 변경은 트랜잭션 안에서 실행**


- **JPQL 소개**
  - JPA를 사용하면 엔티티 객체를 중심으로 개발
  - 문제는 검색 쿼리
  - 검색을 할 때도 **테이블이 아닌 엔티티 객체를 대상으로 검색**
  - 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
  - **애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요**
  - **JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공**
  - SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
  - **JPQL은 엔티티 객체**를 대상으로 쿼리
  - **SQL은 데이터베이스 테이블**을 대상으로 쿼리
  - 테이블이 아닌 **객체를 대상으로 검색하는 객체 지향 쿼리**
  - SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
  - JPQL을 한마디로 정의하면 객체 지향 SQL