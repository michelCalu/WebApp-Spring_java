package be.unamur.hermes.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.DepartmentService;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.dataaccess.entity.Department;

@RestController
@RequestMapping({ "/departments" })
public class DepartmentController implements Errors {

    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
	super();
	this.departmentService = departmentService;
    }

    // READ

    @GetMapping
    public ResponseEntity<List<Department>> findDepartments(
	    @RequestParam("municipalityId") Optional<Long> municipalityId) {
	if (municipalityId.isPresent())
	    return ResponseEntity.ok(departmentService.findAll(municipalityId.get()));
	else
	    throw new BusinessException(MISSING_MUNICIPALITY, "MunicipalityId is required");
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<Department> findDepartment(@PathVariable(value = "departmentId") long departmentId) {
	return ResponseEntity.ok(departmentService.findById(departmentId));
    }
}
