package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.RequestField;

public interface RequestFieldRepository {

    Long createRequestField(RequestField requestField, Long requestID);

    void updateRequestField(RequestField requestField, Long requestID);

    List<RequestField> getFields(Long requestId);

}
