package com.example.rag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfig {

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    ChatClient chatClient(
            ChatModel chatModel,
            VectorStore vectorStore,
            @Value("${app.rag.top-k:4}") int topK,
            @Value("${app.rag.similarity-threshold:0.0}") double similarityThreshold) {
        var advisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                        .topK(topK)
                        .similarityThreshold(similarityThreshold)
                        .build())
                .build();
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                        Size sağlanan bağlamdaki (context) bilgiler ilgiliyse, bu bilgileri kullanarak kullanıcının sorusunu yanıtlayın.
                        Eğer bağlamda ilgili bilgi yoksa veya bağlam yeterli değilse, kendi genel bilginizi kullanarak soruyu yanıtlayın.""")
                .defaultAdvisors(advisor)
                .build();
    }
}
