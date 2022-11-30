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
public class Member7 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER7_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @Embedded
    private Address2 homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD2",
                     joinColumns = @JoinColumn(name = "MEMBER7_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods= new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS",
//            joinColumns = @JoinColumn(name = "MEMBER6_ID"))
//    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // 영속성 전이
    @JoinColumn(name = "MEMBER7_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();
}
