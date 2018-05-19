package be.unamur.hermes.business.service;

import java.util.Map;

import be.unamur.hermes.dataaccess.entity.RequestParameters;

/**
 * Service Interface for parameters.
 */
public interface ParameterService {

    /**
     * 
     * @param municipality
     * @param requestTypeId
     * @param parameterId
     *            (xpath expression relative to the claimType node)
     * @return
     */
    String getParameter(long municipalityId, long requestTypeId, String parameterId);

    RequestParameters getRequestType(long municipalityId, long requestTypeId);

    void create(Map<String, String> parameters, long municipalityId, long requestTypeId);

    void update(Map<String, String> parameters, long municipalityId, long requestTypeId);

}
