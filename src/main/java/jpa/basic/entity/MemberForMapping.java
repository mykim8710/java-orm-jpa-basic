package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor // JPA는 기본생성자를 필요로 한다.
@Entity  // jpa가 관리할 객체
@Table(name = "MemberForMapping") // 맵핑할 db 테이블 명
public class MemberForMapping {
    @Id // 데이터베이스의 PK 컬럼과 맵핑
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

}
