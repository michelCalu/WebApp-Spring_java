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

    departmentId: number;
    requestsCreationDates = new Map<number, Date>();

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
        this.serviceRequests$.subscribe(requests => {
            for (const request of requests) {
                this.requestService.getCreationDate(request.id)
                        .subscribe(creationDate => this.requestsCreationDates[request.id] = creationDate);
            }
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
