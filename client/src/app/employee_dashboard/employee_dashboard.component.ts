import { Component, OnInit } from '@angular/core';

import { RequestService, AuthenticationService, EmployeeService } from '../_services';
import { CitizenRequest, User, Employee } from '../_models';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/mergeMap';

@Component({
    templateUrl: 'employee_dashboard.component.html'
})

export class EmployeeDashboardComponent implements OnInit {

    currentUser: User;
    selectedRequest: CitizenRequest;
    serviceRequests$: Observable<CitizenRequest[]>;
    // employee: Employee;
    departmentId: number;

    constructor(private requestService: RequestService, private employeeService: EmployeeService,
                    private authService: AuthenticationService) {}

    ngOnInit() {
        this.currentUser = this.authService.getCurrentUser();
        const currentEmployee = this.employeeService.getEmployeeById(this.currentUser.id);
        const employeeDepartments = currentEmployee.flatMap(employee => employee.departmentIds);
        this.serviceRequests$ = employeeDepartments.flatMap(departmentId => {
            this.departmentId = departmentId;
            return this.requestService.getDepartmentRequests(departmentId);
        });
    }

    assign (request: CitizenRequest) {
        this.requestService.assignThisRequest(this.currentUser.id, request)
            .subscribe( success => {
                if (success) {
                    this.refresh();
                }
            });
    }

    refresh() {
        this.serviceRequests$ = this.requestService.getDepartmentRequests(this.departmentId);
        this.selectedRequest = null;
    }
}
