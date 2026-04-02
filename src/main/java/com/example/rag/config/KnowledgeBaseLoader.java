package com.example.rag.config;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class KnowledgeBaseLoader {

    @Bean
    ApplicationRunner ingestKnowledgeBase(VectorStore vectorStore, ResourceLoader resourceLoader) {
        return args -> {
            Resource kb = resourceLoader.getResource("classpath:kb/knowledge-base.txt");    
            if (!kb.exists()) {
                throw new IllegalStateException("classpath:kb/knowledge-base.txt bulunamadı");
            }
            String text = kb.getContentAsString(StandardCharsets.UTF_8);
            vectorStore.add(List.of(new Document(text, Map.of("source", "knowledge-base.txt"))));
        };
    }
}
