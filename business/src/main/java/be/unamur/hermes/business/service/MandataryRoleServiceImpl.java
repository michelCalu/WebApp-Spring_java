package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.MandataryRole;
import be.unamur.hermes.dataaccess.repository.MandataryRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MandataryRoleServiceImpl implements MandataryRoleService {

    private final MandataryRoleRepository mandataryRoleRepository;

    @Autowired
    public MandataryRoleServiceImpl(MandataryRoleRepository mandataryRoleRepository) {
        this.mandataryRoleRepository = mandataryRoleRepository;
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
