package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Entity  // jpa가 관리할 객체
public class Member {
    @Id // 데이터베이스의 PK 컬럼과 맵핑
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
