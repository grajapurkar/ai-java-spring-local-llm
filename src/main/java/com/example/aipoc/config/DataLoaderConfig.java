package com.example.aipoc.config;

import com.example.aipoc.model.VectorDocument;
import com.example.aipoc.service.EmbeddingService;
import com.example.aipoc.vector.InMemoryVectorStore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataLoaderConfig {

    private final InMemoryVectorStore vectorStore;

    private final EmbeddingService embeddingService;

    @PostConstruct
    public void loadPolicies() {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    getClass()
                                            .getResourceAsStream(
                                                    "/policies.txt"
                                            )
                            )
                    );

            reader.lines()
                    .filter(line -> !line.isBlank())
                    .forEach(line -> {

                        float[] embedding =
                                embeddingService
                                        .generateEmbedding(line);

                        VectorDocument document =
                                new VectorDocument(
                                        UUID.randomUUID().toString(),
                                        line,
                                        embedding
                                );

                        vectorStore.add(document);
                    });

            System.out.println(
                    "Policies loaded into vector store"
            );

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}