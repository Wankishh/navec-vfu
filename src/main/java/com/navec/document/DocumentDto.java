package com.navec.document;

public record DocumentDto(Long id, DocumentType documentType, String content) {
}
