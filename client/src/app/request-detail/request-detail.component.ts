import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest, Citizen } from '../_models';
import { AuthenticationService, CitizenService, RequestService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'request-detail',
    templateUrl: 'request-detail.component.html'
})

export class RequestDetailComponent implements OnInit {
    @Input()
    request: CitizenRequest;
    @Input()
    displayEmployeeActions: boolean;

    citizen: Citizen;
    selectedAction = 'accept';
    possibleActions = ['accept', 'reject', 'requestModification'];

    constructor(private authService: AuthenticationService, private citizenService: CitizenService,
                private requestService: RequestService) {}

    ngOnInit(): void {
        const currentUser = this.authService.getCurrentUser();
        this.citizenService.getCitizen(currentUser)
            .map(citizen => this.citizen = citizen)
            .subscribe();
    }

    performAction(): void {
        this.requestService.performAction(this.request, this.selectedAction, null);
    }
}
