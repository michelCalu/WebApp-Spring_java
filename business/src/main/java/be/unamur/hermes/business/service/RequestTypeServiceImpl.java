package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.RequestType;
import be.unamur.hermes.dataaccess.repository.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RequestTypeServiceImpl implements RequestTypeService {

    private final RequestTypeRepository requestTypeRepository;

    @Autowired
    RequestTypeServiceImpl(RequestTypeRepository requestTypeRepository){
        this.requestTypeRepository = requestTypeRepository;
    }

    @Override
    public RequestType findById(Long id) {
        return requestTypeRepository.findById(id);
    }

    @Override
    public RequestType findByDescription(String description) {
        return requestTypeRepository.findByDescription(description);
    }

    @Override
    public List<RequestType> findByDepartmentId(Long departmentId) {
        return requestTypeRepository.findByDepartmentId(departmentId);
    }

    @Override
    public List<RequestType> findByMunicipalityId(Long municipalityId) {
        List<RequestType> allRequestType = requestTypeRepository.findByMunicipalityId(municipalityId);
        HashSet<RequestType> tempSet = new HashSet<>(allRequestType);
        return new ArrayList<>(tempSet);
    }
}
