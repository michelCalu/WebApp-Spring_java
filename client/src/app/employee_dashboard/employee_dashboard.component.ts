import { Component, OnInit } from '@angular/core';

import { RequestService, AuthenticationService } from '../_services';
import { CitizenRequest, User } from '../_models';
import { Observable } from 'rxjs/Observable';

@Component({
    templateUrl: 'employee_dashboard.component.html'
})

export class EmployeeDashboardComponent implements OnInit {

    currentUser: User;
    selectedRequest: CitizenRequest;
    serviceRequests$: Observable<CitizenRequest[]>;

    constructor(private requestService: RequestService, private authService: AuthenticationService) {}

    ngOnInit() {
        this.currentUser = this.authService.getCurrentUser();
        this.serviceRequests$ = this.requestService.getDepartmentRequests(5 /* TODO: should be the departmentId of the employee */);
    }

    assign (request: CitizenRequest) {
        this.requestService.assignThisRequest(this.currentUser.id, request)
            .subscribe( /* TODO */ );
    }
}
