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
  - `/META-INF/persistence.xml` 위치
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

- [주의점]
  - **엔티티 매니저 팩토리(emf)**는 하나만 생성해서 애플리케이션 전체에서 공유
  - **엔티티 매니저(em)**는 쓰레드간에 공유X (사용하고 버려야 한다).
  - **JPA의 모든 데이터 변경은 트랜잭션 안에서 실행**

- [**JPQL 소개**]
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

- [영속성 관리 - JPA 내부동작방식] 
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
  
- [엔티티 매핑]
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

- [연관관계 매핑 기초]
  - 연관관계가 필요한 이유
    - ‘객체지향 설계의 목표는 자율적인 객체들의 협력 공동체를 만드는 것이다.’
    - 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다.
      - 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다.
      - 객체는 참조를 사용해서 연관된 객체를 찾는다.
      - 테이블과 객체 사이에는 이런 큰 간격이 있다.
      
  - 단방향 연관관계
    - 단방향 맵핑
    
  - 양방향 연관관계
    - 양방향 매핑
    - 연관관계의 주인과 mappedBy
    - 객체와 테이블이 관계를 맺는 차이
      - ****객체 연관관계 = 단방향이 2개****
      - ****테이블 연관관계 = 1개****
    - 객체의 양방향 관계
      - 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단뱡향 관계 2개다.
      - 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.
    - 테이블의 양방향 연관관계
      - 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
    - 연관관계의 주인(Owner)
      - 양방향 매핑 규칙
        - 객체의 두 관계중 하나를 연관관계의 주인으로 지정
        - 주인은 mappedBy 속성 사용 X
        - **연관관계의 주인만이 외래 키를 관리(등록, 수정)**
        - **주인이 아닌쪽은 읽기만 가능**
        - 주인이 아니면 mappedBy 속성으로 주인 지정
      - 누구를 주인으로?
        - 외래 키가 있는 있는 곳을 주인으로 정해라
    - 양방향 매핑 시 가장 많이 하는 실수
      - 연관관계의 주인에 값을 입력하지 않음
    - 양방향 연관관계 주의
      - 양방향 매핑 시 연관관계의 주인에 값을 입력해야 한다. 
      - 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
      - 연관관계 편의 메소드를 생성 : 한쪽에만 생성
      - 양방향 매핑 시에 무한 루프를 조심
      
  - **양방향 매핑 정리**
    - **단방향 매핑만으로도 이미 연관관계 매핑은 완료(처음에는 무조건 단방향으로 끝내라!!)**
    - 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
    - JPQL에서 역방향으로 탐색할 일이 많음
    - **단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨(테이블에 영향을 주지 않음)**
      - **먼저 단방향으로 끝내고 필요할 때만 양방향 추가**

  - **연관관계의 주인을 정하는 기준**
    - 비즈니스 로직을 기준으로 연관관계의 주인을 선택하면 안됨
    - ****연관관계의 주인은 외래 키의 위치를 기준으로 정해야함**

- [다양한 연관관계 매핑]
  - 연관관계 매핑 시 고려사항 3가지
    - 다중성
      - 현 객체 자기 자신을 기준으로, 앞에 나오는 것이 연관관계의 주인이다.
        - 다대일 : @ManyToOne   →  N : 1
        - 일대다 : @OneToMany   →  1 : N
        - 일대일 : @OneToOne    →  1 : 1
        - 다대다 : @ManyToMany  →  N : M, 실무에서 쓰면 안된다!!
    - 단방향, 양방향
      - **DB 테이블**
        - 외래 키 하나로 양쪽 조인 가능
        - 사실 방향이라는 개념이 없음
      - **객체**
        - 참조용 필드가 있는 쪽으로만 참조 가능
        - 한쪽만 참조하면 단방향
        - 양쪽이 서로 참조하면 양방향(단방향이 두개), 필요할때만 사용
    - 연관관계의 주인
      - **테이블은 외래 키 하나**로 두 테이블이 연관관계를 맺음
      - 객체 양방향 관계는 A ⇒ B, B ⇒ A 처럼 **참조가 2군데**
      - 객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야함
      - **연관관계의 주인 : 외래 키를 관리하는 참조( N )**
      - **주인의 반대편 : 외래 키에 영향을 주지 않음, 단순 조회만 가능 ( 1 )**
      
  - 다대일 [N:1]
  - 일대다 [1:N]
  - 일대일 [1:1]
  - 다대다 [N:M] : 실무에서 사용 X

- [고급 매핑]
  - 상속관계 매핑
    - 관계형 데이터베이스는 상속 관계 X
    - **슈퍼타입 - 서브타입 관계라는 모델링(논리 모델링) 기법**이 객체 상속과 유사
    - 상속관계 매핑
      - 객체의 상속과 구조와 DB의 슈퍼타입 - 서브타입 관계를 매핑
    - 슈퍼타입 - 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법
      - 각각 테이블로 변환 → 조인전략(정석)
      - 통합 테이블로 변환 → 단일 테이블 전략
      - 서브타입 테이블로 변환 → 구현 클래스마다 테이블 전략 : 실무에서 사용 X
    - **주요 어노테이션**
      - @Inheritance(strategy=InheritanceType.XXX)
        - **JOINED**: 조인 전략
        - **SINGLE_TABLE**: 단일 테이블 전략
        - **TABLE_PER_CLASS**: 구현 클래스마다 테이블 전략
      - @DiscriminatorColumn(name=“DTYPE”)
      - @DiscriminatorValue(“XXX”)
      
  - Mapped Superclass : @MappedSuperclass
    - 공통 매핑 정보가 필요할 때 사용 
    - 공통속성만 모아놓은 클래스를 정의하고 이 클래스를 상속받아 사용
    - 상속관계 매핑 X
    - 엔티티 X, 테이블과 매핑 X
    - 부모 클래스를 상속 받는 **자식 클래스에 매핑 정보만 제공**
    - 조회, 검색 불가 : **em.find(BaseEntity.class) 불가**
    - 직접 생성해서 사용할 일이 없으므로 **추상 클래스 권장**
    - 테이블과 관계 없고, 단**순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할**
    - 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용
    - 참고: 엔티티 클래스는 @Entity나 @MappedSuperclass로 지정한 클래스만 상속 가능

- [프록시와 연관관계 관리]
  - 프록시 기초
    - **em.find()** vs em.**getReference()**
      - **em.find()** : DB를 통해서 실제 엔티티 객체 조회
      - **em.getReference()** : DB 조회를 미루는 가짜(프록시) 엔티티 객체 조회
    - 프록시 특징
      - 실제 클래스를 상속 받아서 만들어짐
      - 실제 클래스와 겉 모양이 같다.
      - 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨(이론상)
      - 프록시 객체는 실제 객체의 참조(target)를 보관
      - 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출
    - 프록시 객체의 초기화
      - 프록시 객체는 **처음 사용 할 때 한번만 초기화**
      - 프록시 객체를 초기화 할 때,
        - 프록시 객체가 실제 엔티티로 바뀌는 것은 아님
        - 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
      - 프록시 객체는 원본 엔티티를 상속받음
        - **따라서 타입 체크시 주의해야함 (== 비교 실패, 대신 instance of 사용)**
      - 영속성 컨텍스트(1차 캐시)에 찾는 엔티티가 이미 있으면 **em.getReference()**를 호출해도 실제 엔티티 반환
      - 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
        - 하이버네이트는 `**org.hibernate.LazyInitializationException`** 예외를 터트림
    - **프록시 확인**
      - **프록시 인스턴스의 초기화 여부 확인**
        - `PersistenceUnitUtil.isLoaded(Object entity)`
        - `emf.getPersistenceUnitUtil().isLoaded(Object entity);`
      - **프록시 클래스 확인 방법**
        - `entity.getClass().getName()`
        - 출력(..javasist.. or HibernateProxy...)
      - **프록시 강제 초기화**
        - `org.hibernate.Hibernate.initialize(Object entity);`
        - `Hibernate.initialize(Object entity);`
      - 참고: JPA 표준은 강제 초기화 없음
  
  - 즉시로딩과 지연로딩
    - 지연로딩 @ManyToOne(fetch = FetchType.LAZY)
    - 즉시로딩 @ManyToOne(fetch = FetchType.EAGER)
    - **프록시와 즉시로딩 주의**
      - **가급적 지연 로딩만 사용(특히 실무에서)**
        - 테이블이 많을수록 전부 다 조인, 성능저하
        - 실무에서는 먼저 LAZY로 설정하고 필요할때만 따로 ****JPQL fetch 조인이나, 엔티티 그래프 기능 사용****
      - 즉시 로딩을 적용하면 **예상하지 못한 SQL**이 발생
      - 즉시 로딩은 **JPQL에서 N+1 문제**를 일으킨다.
        - N + 1
          - 1이 최초 쿼리
          - 결과의 개수 N개
          - 쿼리는 최초쿼리 1개를 날렸지만 그에 따라 N개의 추가 쿼리가 날라감
  
  - 영속성 전이(CASCADE)와 고아 객체
    - 영속성 전이: CASCADE
      - @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
      - **영속성 전이: CASCADE - 주의!**
        - 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없음
        - 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공할 뿐
        - 일대다에서 무조건 다 걸어야하는 것은 아니다.
        - 여러 곳과 연관관계인 것에서는 사용하지 말자. 단일소유일때만 사용
      - **CASCADE Option의 종류**
        - **ALL: 모두 적용**
        - **PERSIST: 영속(저장)**
        - **REMOVE: 삭제**
        - MERGE: 병합
        - REFRESH: REFRESH
        - DETACH: DETACH
    - 고아 객체
      - 고아 객체 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
      - Option : **orphanRemoval = true**
      - **고아 객체 - 주의**
        - 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능
        - **참조하는 곳이 하나일 때 사용해야함!**
        - **특정 엔티티가 개인소유 할 때 사용**
        - @OneToOne, @OneToMany만 가능
        - 참고
          - 개념적으로 부모를 제거하면 자식은 고아가 된다.
          - 따라서 고아 객체 제거 기능을 활성화 하면, 부모를 제거할 때 자식도 함께 제거된다.
          - 이것은 CascadeType.REMOVE처럼 동작한다.
    - 영속성 전이 + 고아 객체, 생명주기
      - ****CascadeType.ALL + orphanRemovel=true****
      - 스스로 생명주기를 관리하는 엔티티는 **em.persist()로 영속화, em.remove()로 제거**
      - 두 옵션을 모두 활성화 하면 **부모 엔티티를 통해서 자식의 생명 주기를 관리**할 수 있음
      - 도메인 주도 설계(DDD)의 Aggregate Root개념을 구현할 때 유용

- [값 타입]
  - 기본값 타입
    - **JPA의 데이터 타입 분류**
      - ****엔티티 타입****
        - @Entity로 정의하는 class 체
        - 데이터가 변해도 식별자(pk, id)로 지속해서 **추적 가능**
        - 예) 회원 엔티티의 키나 나이 값을 변경해도 식별자(pk, id)로 인식가능
      - ****값 타입****
        - int, Integer, String처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
        - 식별자가 없고 값만 있으므로 변경 시 추적 불가
        - 예) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체
  
  - 임베디드 타입
    - 새로운 값 타입을 직접 정의할 수 있음
    - JPA는 임베디드 타입(embedded type)이라 함
    - 주로 기본 값 타입을 모아서 만들어서 복합 값 타입이라고도 함
    - int, String과 같은 값 타입
    - **임베디드 타입 사용법**
      - @Embeddable: 값 타입을 정의하는 곳에 표시
      - @Embedded: 값 타입을 사용하는 곳에 표시
      - 기본 생성자 필수
    - **임베디드 타입의 장점**
      - 재사용
      - 높은 응집도
      - Period.isWork()처럼 해당 값 타입만 사용하는 의미 있는 메소드를 만들 수 있음
      - 임베디드 타입을 포함한 모든 값 타입은, 값 타입을 소유한 엔티티에 생명주기를 의존함
    - 임베디드 타입과 테이블 매핑
      - 임베디드 타입은 **엔티티의 값일 뿐**이다.
      - 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
      - **객체와 테이블을 아주 세밀하게(find-grained) 매핑하는 것이 가능**
      - 잘 설계한 ORM 애플리케이션은 **매핑한 테이블의 수보다 클래스의 수가 더 많음**
    - 임베디드 타입과 연관관계
      - embedded 타입은 또 다른 embedded 타입을 가질수 있다.
      - embedded 타입이 entity 타입을 가질수 있다.
    - **@AttributeOverride: 속성 재정의**
      - 한 엔티티에서 같은 값 타입을 사용하면?
      - 컬럼 명이 중복됨
      - @AttributeOverrides, @AttributeOverride를 사용해서 컬러 명 속성을 재정의
    - 임베디드 타입과 null : 임베디드 타입의 값이 null이면 매핑한 컬럼 값은 모두 null
  
  - 값 타입과 불변 객체
    - 값 타입은 **복잡한 객체 세상을 조금이라도 단순화**하려고 만든 개념이다.
    - 따라서 값 타입은 **단순하고 안전하게 다룰 수 있어야** 한다.
    - **값 타입 공유 참조**
      - **임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함**
      - 공유하려면 임베디드가 아닌 엔티티로 사용해야한다.
      - 부작용(side effect) 발생
    - **객체 타입의 한계**
      - 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
      - 문제는 **임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입**이다.
      - 자바 기본 타입에 값을 대입하면 값을 복사한다.
      - **객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.**
      - **객체의 공유 참조는 피할 수 없다.**
    - **불변 객체**
      - 객체 타입을 수정할 수 없게 만들면 **부작용을 원천 차단**
      - **값 타입은 불변 객체(immutable object)로 설계해야함**
      - **불변 객체 : 생성 시점 이후 절대 값을 변경할 수 없는 객체**
      - 생성자로만 값을 설정하고 **수정자(Setter)를 만들지 않으면 됨**
      - 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체
      - **불변이라는 작은 제약으로 부작용이라는 큰 재앙을 막을 수 있다.**
      - 실제 값을 바꾸고 싶다면?? : 객체를 새로 생성한다.
  - 값 타입의 비교
    - 값 타입: 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야함
    - **동일성(identity) 비교** : 인스턴스의 참조 값을 비교, == 사용
    - **동등성(equivalence) 비교** : 인스턴스의 value 값을 비교, equals() 사용
    - 값 타입은 a.equals(b)를 사용해서 동등성 비교를 해야함
    - 값 타입의 equals() 메소드를 적절하게 재정의(주로 모든 필드 사용)
  
  - 값타입 컬렉션
    - 값 타입을 하나 이상 저장할 때 사용
    - **@ElementCollection, @CollectionTable** 사용
    - 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
    - 컬렉션을 저장하기 위한 별도의 테이블이 필요함

    - **값 타입 컬렉션의 제약사항**
      - 값 타입은 엔티티와 다르게 식별자 개념이 없다.
      - • 값은 변경하면 추적이 어렵다.
      - 값 타입 컬렉션에 변경 사항이 발생하면, **주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장**한다.
        - 위험하다, 실무에서 사용 X
      - 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 함 : **null 입력 X, 중복 저장 X**
  
    - 값 타입 컬렉션 대안
      - 실무에서는 상황에 따라 **값 타입 컬렉션 대신에 일대다 관계를 고려**
      - 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
      - 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션 처럼 사용

    - ****엔티티 타입의 특징****
      - 식별자 O(pk)
      - 생명주기관리
      - 공유
      
    - ****값 타입의 특징****
      - 식별자X
      - 생명 주기를 엔티티에 의존
      - 공유하지 않는 것이 안전(복사해서 사용)
      - 불변객체로 만드는 것이 안전
      
    - **값 타입은 정말 값 타입이라 판단될 때만 사용**
    - **엔티티와 값 타입을 혼동해서 엔티티를 값 타입으로 만들면 안됨**
    - **식별자가 필요하고, 지속해서 값을 추적, 변경해야 한다면 그것은 값 타입이 아닌 엔티티**

- [객체지향 쿼리언어]
  - 소개
    - JPQL
      - **JPA를 사용하면 엔티티 객체를 중심으로 개발**
      - 문제는 검색 쿼리
      - 검색을 할 때도 **테이블이 아닌 엔티티 객체를 대상으로 검색**
      - 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
      - 애플리케이션이 **필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요**
      - JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
      - SQL과 문법 유사
        - `SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN` 지원
      - JPQL은 엔티티 객체를 대상으로 쿼리(JPQL → SQL 번역)
      - SQL은 데이터베이스 테이블을 대상으로 쿼리
      - 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
      - SQL을 추상화해서 특정 데이터베이스 SQL에 의존 X
      - JPQL을 한마디로 정의하면 객체 지향 SQL
    - Criteria
      - 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
      - JPQL 빌더 역할, 동적쿼리를 짜는데 있어서 좀 더 편리
      - JPA 공식 기능
      - **단점 : 너무 복잡하고 실용성이 없다.**
      - Criteria 대신에 **QueryDSL 사용 권장**
      - 실무에서 거의 사용하지 않는다.
    - QueryDSL
      - 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
      - JPQL 빌더 역할
      - 컴파일 시점에 문법 오류를 찾을 수 있음
      - 동적쿼리 작성 편리함
      - **단순하고 쉬움**
      - **실무 사용 권장**
      - 사용을 위한 사전 setting이 필요
      - jpql 문법을 잘 알면 QueryDSL은 자연스럽게 터득
      - www.querydsl.com
    - 네이티브 SQL 소개
      - JPA가 제공하는 SQL을 직접 사용하는 기능
      - JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
      - 예) 오라클 CONNECT BY, 특정 DB만 사용하는 SQL 힌트
    - **JDBC 직접 사용, SpringJdbcTemplate 등**
      - JPA를 사용하면서 JDBC 커넥션을 직접 사용하거나, 스프링 JdbcTemplate, 마이바티스등을 함께 사용 가능
      - **단 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요**
      - 예) JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트 수동 플러시

  - JPQL : Java Persistence Query Language
    - 