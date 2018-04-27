package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.*;

public interface EventService {

    Event findByReq (Long requestID);

    Event findByCitizen (Long citizenID);

    Event findByCompany (String entrepriseNb);

    Event findByEmployeen (Long employeeID);


}
