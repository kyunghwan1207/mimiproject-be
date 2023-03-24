package com.example.emart.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "advertisements")
@Setter @Getter
@NoArgsConstructor
public class Advertisement {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String img;
    private String link;

    public Advertisement(String name, String img, String link) {
        this.name = name;
        this.img = img;
        this.link = link;
    }
}
