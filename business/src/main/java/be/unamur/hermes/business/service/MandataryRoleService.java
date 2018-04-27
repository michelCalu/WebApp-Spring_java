package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.MandataryRole;

import java.util.List;

public interface MandataryRoleService {

    MandataryRole findByMandatary (Long mandataryID);

    List<MandataryRole> findByCompany (Long companyID);

    MandataryRole findByCitizen (Long citizenID);
}
