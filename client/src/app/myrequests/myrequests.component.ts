import { Component, OnInit } from '@angular/core';

import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';
import { RequestService, MockAuthService } from '../_services';
import { Observable } from 'rxjs/Observable';
import { CitizenRequest } from '../_models/citizen-request.model';

@Component({
    moduleId: module.id,
    templateUrl: 'myrequests.component.html'
})

export class MyRequestsComponent implements OnInit {

    citizenRequests$: Observable<CitizenRequest[]>;

    constructor(private requestService: RequestService, private authService: MockAuthService) {}

    ngOnInit() {
        const currentUser = this.authService.getUser();
        this.citizenRequests$ = this.requestService.getCitizenRequests(currentUser.id);
    }
}
