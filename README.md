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

- 영속성 관리 - JPA 내부동작방식 
  - 영속성 컨택스트 : "엔티티를 영구 저장하는 환경"이라는 뜻
  - 엔티티의 생명주기
    - 비영속(new/transient)
    - 영속(managed)
    - 준영속(detached)
    - 삭제(removed)
  - 영속성 컨텍스트의 이점
    - 1차 캐시
    - 동일성(identity) 보장
    - 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
    - 변경 감지(Dirty Checking) : 엔티티 수정
    - 지연 로딩(Lazy Loading)
  - 플러시 : 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
    - - 영속성 컨텍스트를 비우지 않음
    - **영속성 컨텍스트의 변경내용을 데이터베이스에 동기화**
    - 트랜잭션이라는 작업 단위가 중요 → 커밋 직전에만 동기화 하면 됨
  - 준영속 상태 
    - 영속(헤당 엔티티가 영속성 컨택스트의 1차캐시에 올라와있는 상태,  jpa가 관리하는 상태) → 준영속
    - 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
    - 영속성 컨텍스트가 제공하는 기능을 사용 못함
  
- 엔티티 매핑
  - 객체와 테이블 맵핑
    - 엔티티 매핑 java @annotation
      - 객체와 db 테이블 매핑 : @Entity, @Table
      - 필드와 db 컬럼 매핑 : @Column
      - 기본 키(pk) 매핑 : @Id
      - 연관관계(일대일, 다대일, 일대다, 다대다…) 매핑: @ManyToOne, @OneToMany, @JoinColumn...
    - @Entity
      - @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
      - JPA를 사용해서 db 테이블과 매핑할 클래스(domain, entity class)는 @Entity 필수
      - 주의
        - 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자), jpa spec
        - final 클래스, enum, interface, inner 클래스 사용 X
        - 저장할 필드에 final 사용 X
    - @Table
      - @Table은 엔티티와 매핑할 db 테이블 명을 지정
  - 데이터베이스 스키마 자동생성
    - DDL은 애플리케이션 실행 시점에 DB 자동 생성
    - 테이블 중심 → 객체 중심
    - 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 DDL 생성
    - 이렇게 **생성된 DDL은 개발 장비에서만 사용**
    - 생성된 DDL은 운영서버에서는 사용하지 않거나, 적절히 다듬은 후 사용
    - 속성 : <property name="hibernate.hbm2ddl.auto" value="create" /> in persistance.xml
      ```
        in persistance.xml
      
        <?xml version="1.0" encoding="UTF-8"?>
          <persistence version="2.2"
                xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
          <persistence-unit name="hello">
            <properties>
              <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
              <property name="javax.persistence.jdbc.user" value="sa"/>
              <property name="javax.persistence.jdbc.password" value=""/>
              <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
              <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>

              <property name="hibernate.show_sql" value="true"/>
              <property name="hibernate.format_sql" value="true"/>
              <property name="hibernate.use_sql_comments" value="true"/>
              <property name="hibernate.jdbc.batch_size" value="10"/> 
              <property name="hibernate.hbm2ddl.auto" value="create" /> 
           </properties>
         </persistence-unit>
       </persistence>
      ```
      - create : 기존 테이블 삭제 후 다시 생성(DROP + CREATE)
      - create-drop : create와 같으나 종료시점에 테이블 DROP
      - update : 변경분만 반영(운영 DB에 사용하면 안됨)
      - validate : 엔티티와 테이블이 정상 맵핑되었는지만 확인
      - none : 사용하지 않음
    - 주의
      - **!! 운영 장비에는 절대 create, create-drop, update 사용하면 안된다. !!**
      - 개발 초기 단계는 create 또는 update
      - 테스트 서버는 update 또는 validate
      - 스테이징과 운영 서버는 validate 또는 none
    - DDL 생성 기능
      - 제약조건 추가: ex) 회원 이름은 필수, 10자 초과X
        - **@Column(nullable = false, length = 10)**
      - 유니크 제약조건 추가
        - **@Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"} )})**
      - DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.
  - 필드와 컬럼맵핑
    - 매핑 어노테이션 정리
      - @Column : db 컬럼 매핑
      - @Temporal : 날짜 타입 매핑
      - @Enumerated : enum 타입 매핑
      - @Lob : BLOB, CLOB 매핑
      - @Transient : 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
    - 기본키 맵핑
      - @Id, 직접 할당
      - @GeneratedValue, 자동 생성
        - IDENTITY: 데이터베이스에 위임, **MYSQL(auto increment)**
        - SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, **ORACLE**
          - @SequenceGenerator 필요
        - TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용
          - @TableGenerator 필요
        - AUTO: 방언에 따라 자동 지정, 기본값
    - 권장하는 식별자 전략
      - **기본 키 제약 조건 : null 아님, 유일, 변하면 안된다.**
      - 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. **대리키(대체키)를 사용**하자.
      - **예를 들어 주민등록번호도 기본 키로 적절하기 않다.**
      - 권장: Long형 + 대체키(ex. UUID) + 키 생성전략 사용