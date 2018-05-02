import { Component, OnInit } from '@angular/core';

import { RequestService, AuthenticationService } from '../_services';
import { CitizenRequest } from '../_models';
import { Observable } from 'rxjs/Observable';

@Component({
    templateUrl: 'employee_dashboard.component.html'
})

export class EmployeeDashboardComponent implements OnInit {

    serviceRequests$: Observable<CitizenRequest[]>;

    constructor(private requestService: RequestService, private authService: AuthenticationService) {}

    ngOnInit() {
        const currentUser = this.authService.getCurrentUser();
        this.serviceRequests$ = this.requestService.getServiceRequests(currentUser.id);
    }
}
