package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.MandataryRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MandataryRoleServiceImpl implements MandataryRoleService {

    private MandataryRoleService mandataryRoleService;

    @Autowired
    public MandataryRoleServiceImpl(MandataryRoleService mandataryRoleService) {
        this.mandataryRoleService = mandataryRoleService;
    }

    @Override
    public MandataryRole findByMandatary(Long mandataryID) {
        return null;
    }

    @Override
    public List<MandataryRole> findByCompany(Long companyID) {
        return null;
    }

    @Override
    public MandataryRole findByCitizen(Long citizenID) {
        return null;
    }
}
