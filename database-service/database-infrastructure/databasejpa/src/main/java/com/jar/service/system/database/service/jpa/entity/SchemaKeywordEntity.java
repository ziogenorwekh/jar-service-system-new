package com.jar.service.system.database.service.jpa.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCHEMA_KEYWORD_ENTITY")
@NoArgsConstructor
public class SchemaKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(value = AccessLevel.PUBLIC)
    @Column(name = "RESERVED_WORD")
    private String reservedWord;

    public SchemaKeywordEntity(String reservedWord) {
        this.reservedWord = reservedWord;
    }
}
