package com.jar.service.system.database.service.jpa;

import com.jar.service.system.database.service.jpa.entity.SchemaKeywordEntity;
import com.jar.service.system.database.service.jpa.repository.SchemaKeywordJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SchemaKeywordDataLoader implements CommandLineRunner {

    private final SchemaKeywordJpaRepository schemaKeywordJpaRepository;

    @Value("${reserve.keywords}") // YAML 파일에서 값을 가져옵니다.
    private List<String> reservedWords;

    @Autowired
    public SchemaKeywordDataLoader(SchemaKeywordJpaRepository schemaKeywordJpaRepository) {
        this.schemaKeywordJpaRepository = schemaKeywordJpaRepository;
    }

    @Override
    public void run(String... args) {
        log.info("start!");
        for (String reservedWord : reservedWords) {
            SchemaKeywordEntity entity = new SchemaKeywordEntity(reservedWord);
            schemaKeywordJpaRepository.save(entity);
        }
    }
}