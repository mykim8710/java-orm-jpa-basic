package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member2 {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "USERNAME")
    private String name;
}
