package com.navec.document;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents")
@Tag(name = "Document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(path = "/privacy-policy")
    public ResponseEntity<DocumentDto> getPrivacyPolicy() {
        return ResponseEntity.ok(this.documentService.getDocument(DocumentType.PRIVACY_POLICY));
    }

    @GetMapping(path = "/terms")
    public ResponseEntity<DocumentDto> getTermsOfUse() {
        return ResponseEntity.ok(this.documentService.getDocument(DocumentType.TERMS_OF_USE));
    }
}
