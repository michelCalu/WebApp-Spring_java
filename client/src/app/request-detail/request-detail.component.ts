import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest, Citizen } from '../_models';
import { AuthenticationService, CitizenService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'request-detail',
    templateUrl: 'request-detail.component.html'
})

export class RequestDetailComponent implements OnInit {
    @Input()
    request: CitizenRequest;
    citizen: Citizen;

    constructor(private authService: AuthenticationService, private citizenService: CitizenService) {}

    ngOnInit(): void {
        const currentUser = this.authService.getCurrentUser();
        this.citizenService.getCitizen(currentUser)
            .map(citizen => this.citizen = citizen)
            .subscribe();
    }
}
