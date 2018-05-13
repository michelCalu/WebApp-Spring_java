import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Company } from '../_models/company.model';
import { CompanyService } from '../_services/company.service';
import { EmployeeService, AuthenticationService, DepartmentService } from '../_services';
import { Employee } from '../_models/employee.model';
import { Department, User } from '../_models';


@Component({
    templateUrl: 'validate_companies.component.html'
})

export class ValidateCompaniesComponent implements OnInit {

    employeeData$: Observable<Employee>;
    pendingCompanies$: Observable<Company[]>;
    employeeDepartment: Department;

    currentUser: User;
    selectedCompany: Company;

    constructor(private companyService: CompanyService, private employeeService: EmployeeService,
                 private authService: AuthenticationService, private departmentService: DepartmentService) { }

    ngOnInit() {
        this.currentUser = this.authService.getCurrentUser();
        this.employeeData$ = this.employeeService.getEmployeeById(this.currentUser.id);
        const employeeDepartment$ = this.employeeData$.flatMap(emp => this.departmentService.getDepartment(emp.departmentIds[0]));
        this.pendingCompanies$ = employeeDepartment$.flatMap (department => {
            this.employeeDepartment = department;
            return this.companyService.getPendingCompanies(department.municipality.id);
        });
    }

    validate(company: Company) {
        this.companyService.validateCompany(company.companyNb).subscribe(success => {
            if (success) {
                // refresh the pending companies
                this._refresh();
            }
        });
    }

    private _refresh() {
        this.pendingCompanies$ = this.companyService.getPendingCompanies(this.employeeDepartment.municipality.id);
        this.selectedCompany = null;
    }
}
