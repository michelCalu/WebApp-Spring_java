package be.unamur.hermes.dataaccess.repository;

import java.util.List;

public interface DocumentRepository {

    long create(long requestId, String contents, long documentTitle);

    String getDocument(long documentId);

    List<Long> getDocumentIds(long requestId);
}
