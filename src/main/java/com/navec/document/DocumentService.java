package com.navec.document;

import com.navec.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentDto getDocument(DocumentType documentType) {
        Document document = this.documentRepository.findByType(documentType)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND));

        return new DocumentDto(document.getId(), document.getType(), document.getContent());
    }
}
