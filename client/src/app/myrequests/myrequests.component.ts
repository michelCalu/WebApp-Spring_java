import { Component, OnInit } from '@angular/core';

import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';
import { RequestService, MockAuthService } from '../_services';
import { Observable } from 'rxjs/Observable';
import { CitizenRequest } from '../_models/citizen-request.model';
import { TranslateService } from '@ngx-translate/core';

@Component({
    moduleId: module.id,
    templateUrl: 'myrequests.component.html'
})

export class MyRequestsComponent implements OnInit {

    citizenRequests$: Observable<CitizenRequest[]>;

    constructor(private requestService: RequestService, private authService: MockAuthService, translate: TranslateService) {
        // TODO this should be done once, somewhere, for the whole application
        // language and fallback language
        translate.setDefaultLang('fr');
        translate.use('fr');
    }

    ngOnInit() {
        const currentUser = this.authService.getUser();
        this.citizenRequests$ = this.requestService.getCitizenRequests(currentUser.id);
    }
}
