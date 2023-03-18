package com.navec.document;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByType(DocumentType type);
}