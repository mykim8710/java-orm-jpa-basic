package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Article extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "ARTICLE_ID")
    private Long id;
    private String author;
}
