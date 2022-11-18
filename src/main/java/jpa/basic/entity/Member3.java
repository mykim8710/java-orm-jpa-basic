package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member3 { // N
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;
}
