package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;

import java.util.List;

public interface RequestFieldRepository {

    Long createRequestField(RequestField requestField, Long requestID);

    List<RequestField> getFields(Long requestId);

}
