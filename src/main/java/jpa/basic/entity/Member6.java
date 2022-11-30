package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member6 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER6_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @Embedded
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD",
                     joinColumns = @JoinColumn(name = "MEMBER6_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods= new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS",
                     joinColumns = @JoinColumn(name = "MEMBER6_ID"))
    private List<Address> addressHistory = new ArrayList<>();
}
