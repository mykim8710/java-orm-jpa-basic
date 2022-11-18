package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@ToString : X
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Team2 { // 1
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;


    List<Member3> members = new ArrayList<>();
}
