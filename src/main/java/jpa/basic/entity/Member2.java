package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// @ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member2 { // N
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Team객체를 프록시 객체로 조회
    @JoinColumn(name = "TEAM_ID")       // join할 DB table의 컬럼명(FK)
    private Team team;

    @Column(name = "USERNAME")
    private String name;

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
