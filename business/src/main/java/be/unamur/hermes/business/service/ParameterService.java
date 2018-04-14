package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.business.parameters.RequestParameters;
import be.unamur.hermes.common.enums.ClaimId;

/**
 * Service Interface for parameters.
 */
public interface ParameterService {

    /**
     * 
     * @param municipality
     * @param claimId
     *            see {@link ClaimId}
     * @return {@code true} if this type of claim is active for the municipality
     *         given in parameter, {@code false} otherwise.
     */
    boolean isActivated(String municipality, String claimId);

    /**
     * 
     * @param municipality
     * @param claimId
     *            see {@link ClaimId}
     * @param parameterId
     *            (xpath expression relative to the claimType node)
     * @return
     */
    String getParameter(String municipality, String claimId, String parameterId);

    RequestParameters getRequestType(String municipality, String claimId);

    List<RequestParameters> getRequestTypes(String municipality);

}
