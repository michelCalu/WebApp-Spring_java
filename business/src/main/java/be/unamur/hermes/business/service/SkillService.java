package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Skill;

import java.util.List;

public interface SkillService {

    List<Skill> findByMunicipality (Long municipalityID);

    List<Skill> findByDpt (Long dptID, Long MunicipalityID);

    void attachToDpt (Long skillID, Long dptID, Long MunicipalityID);

    void detachFromDpt (Long skillID, Long dptID, Long MunicipalityID);




}
