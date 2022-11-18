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

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID") // join할 DB table의 컬럼명(FK)
    private Team team;

    @Column(name = "USERNAME")
    private String name;

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
