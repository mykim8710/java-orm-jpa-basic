package jpa.basic.entity;

import javax.persistence.*;

@Entity  // jpa가 관리할 객체
@Table(name = "MemberForMapping") // 맵핑할 db 테이블 명
public class MemberForMapping {
    @Id // 데이터베이스의 PK 컬럼과 맵핑
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    // JPA는 기본생성자를 필요로 한다.
    public MemberForMapping() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
