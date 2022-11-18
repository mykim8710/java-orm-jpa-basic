package jpa.basic.entity;

import javax.persistence.*;
import java.util.Date;

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


    // JPA는 기본생성자를 필요로 한다.
    public Member() {
    }

    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    // getter
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    // setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
