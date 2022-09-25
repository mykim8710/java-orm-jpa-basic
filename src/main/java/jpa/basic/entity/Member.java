package jpa.basic.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // jpa가 관리할 객체
public class Member {
    @Id // 데이터베이스의 PK와 맵핑
    private Long id;
    private String name;

    // JPA는 기본생성자를 필요로 한다.
    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
