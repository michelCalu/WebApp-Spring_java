import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Company } from '../_models/company.model';
import { CompanyService } from '../_services/company.service';
import { EmployeeService, AuthenticationService, DepartmentService } from '../_services';
import { Employee } from '../_models/employee.model';
import { Department } from '../_models';


@Component({
    templateUrl: 'validate_companies.component.html'
})

export class ValidateCompaniesComponent implements OnInit {

    employeeData$: Observable<Employee>;
    pendingCompanies$: Observable<Company[]>;
    employeeDepartment$: Observable<Department>;

    selectedCompany: Company;

    constructor(private companyService: CompanyService, private employeeService: EmployeeService,
                 private authService: AuthenticationService, private departmentService: DepartmentService) { }

    ngOnInit() {
        const currentUser = this.authService.getCurrentUser();
        this.employeeData$ = this.employeeService.getEmployeeById(currentUser.id);
        this.employeeDepartment$ = this.employeeData$.flatMap(emp => this.departmentService.getDepartment(emp.departmentIds[0]));
        this.pendingCompanies$ = this.employeeDepartment$.flatMap (department =>
                        this.companyService.getPendingCompanies(department.municipality.id));
    }

    // validate(citizen: Citizen) {
    //     this.citizenService.validateCitizenAccount(citizen).subscribe(success => {
    //         if (success) {
    //             this.pendingCitizens$ = this.citizenService.getPendingCitizens();
    //         }
    //     });
    // }
}
