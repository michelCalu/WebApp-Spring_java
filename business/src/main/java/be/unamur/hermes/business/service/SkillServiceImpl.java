package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Skill;
import be.unamur.hermes.dataaccess.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service public class SkillServiceImpl implements SkillService {

    private SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> findByMunicipality(Long municipalityID) {
        return null;
    }

    @Override
    public List<Skill> findByDpt(Long dptID, Long MunicipalityID) {
        return null;
    }

    @Override
    public void attachToDpt(Long skillID, Long dptID, Long MunicipalityID) {

    }

    @Override
    public void detachFromDpt(Long skillID, Long dptID, Long MunicipalityID) {

    }
}
