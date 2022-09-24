package jpa.basic.ex1;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // jpa가 관리할 객체
public class Member {
    @Id // 데이터베이스의 PK와 맵핑
    private Long id;
    private String name;

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
