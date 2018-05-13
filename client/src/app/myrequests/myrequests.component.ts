import { Component, OnInit } from '@angular/core';

import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';
import { RequestService, MockAuthService } from '../_services';
import { Observable } from 'rxjs/Observable';
import { CitizenRequest } from '../_models/citizen-request.model';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
    moduleId: module.id,
    templateUrl: 'myrequests.component.html'
})

export class MyRequestsComponent implements OnInit {

    citizenRequests$: Observable<CitizenRequest[]>;
    selectedRequest: CitizenRequest;

    constructor(private requestService: RequestService, private authService: AuthenticationService) {}

    ngOnInit() {
        const currentUser = this.authService.getCurrentUser();
        this.citizenRequests$ = this.requestService.getCitizenRequests(currentUser.id);

        this.citizenRequests$.subscribe(citizenREquests => {
            console.log(citizenREquests);
        })
    }
}
