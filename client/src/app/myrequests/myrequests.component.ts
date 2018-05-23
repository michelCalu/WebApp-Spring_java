import { Component, OnInit } from '@angular/core';

import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';
import { RequestService, MockAuthService } from '../_services';
import { Observable } from 'rxjs/Observable';
import { CitizenRequest } from '../_models/citizen-request.model';
import { AuthenticationService } from '../_services/authentication.service';
import { Company } from '../_models/company.model';
import { Router } from '@angular/router';

@Component({
    moduleId: module.id,
    templateUrl: 'myrequests.component.html'
})

export class MyRequestsComponent implements OnInit {

    currentCompany: Company;

    citizenRequests$: Observable<CitizenRequest[]>;
    selectedRequest: CitizenRequest;
    citizenRequestsCreationDates = new Map<number, Date>();

    constructor(private requestService: RequestService, private authService: AuthenticationService, private router: Router) {}

    ngOnInit() {
        const currentUser = this.authService.getCurrentUser();
        this.currentCompany = this.authService.getCurrentCompany();
        if (this.currentCompany) {
            this.citizenRequests$ = this.requestService.getCompanyRequests(this.currentCompany.companyNb);
        } else {
            this.citizenRequests$ = this.requestService.getCitizenRequests(currentUser.id);
        }
        this.citizenRequests$.subscribe(requests => {
            for (const request of requests) {
                this.requestService.getCreationDate(request.id)
                        .subscribe(creationDate => this.citizenRequestsCreationDates[request.id] = creationDate);
            }
        });
    }

}
