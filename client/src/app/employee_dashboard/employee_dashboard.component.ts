import { Component, OnInit } from '@angular/core';

import { RequestService, MockAuthService } from '../_services';

@Component({
    templateUrl: 'employee_dashboard.component.html'
})

export class EmployeeDashboardComponent implements OnInit {

    constructor(private requestService: RequestService, private authService: MockAuthService) {}

    ngOnInit() {
        const currentUser = this.authService.getUser();
    }
}
