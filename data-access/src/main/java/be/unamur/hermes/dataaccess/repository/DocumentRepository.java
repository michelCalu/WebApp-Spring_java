package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Document;

import java.util.List;

public interface DocumentRepository {

    long create(long requestId, String contents, String documentTitle);

    String getDocument(long documentId);

    List<Long> getDocumentIds(long requestId);

    public List<Document> getDocumentIdsTitles(long requestId);

    long getDocumentTitleId(String documentTitle);
}
