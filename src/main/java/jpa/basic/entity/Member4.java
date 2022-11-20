package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member4 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER4_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER4_PRODUCT")
//    private List<Product> products = new ArrayList<>();

        @OneToMany(mappedBy = "member")
        private List<Member4Product> memberProducts = new ArrayList<>();
}
