package jpa.basic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Parent { // 1
    @Id @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true) // 양방향 매핑
    private List<Child> childList = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addChild(Child child) {
        this.childList.add(child);
        child.setParent(this);
    }

    public Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }
}
