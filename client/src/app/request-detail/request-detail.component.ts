import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CitizenRequest, Citizen } from '../_models';
import { AuthenticationService, CitizenService, RequestService, AlertService } from '../_services';
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
    @Output()
    requestUpdated = new EventEmitter<void>();

    citizen: Citizen;
    selectedAction = 'accept';
    possibleActions = ['accept', 'reject', 'requestModification'];

    constructor(private authService: AuthenticationService, private citizenService: CitizenService,
                private requestService: RequestService, private alertService: AlertService) {}

    ngOnInit(): void {
        this.citizen = this.request.citizen;
    }

    performAction(): void {
        this.requestService.performAction(this.request, this.selectedAction, null).subscribe(success => {
          if (success) {
            this.alertService.success('Action performed !');
            this.requestUpdated.emit();
          } else {
            this.alertService.error('Action failed !');
          }
        });
    }
}
