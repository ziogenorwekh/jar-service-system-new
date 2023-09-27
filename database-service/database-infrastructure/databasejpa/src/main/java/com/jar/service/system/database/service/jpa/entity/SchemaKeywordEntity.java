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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchemaKeywordEntity that = (SchemaKeywordEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
