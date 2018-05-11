import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CitizenRequest, Citizen } from '../_models';
import { AuthenticationService, CitizenService, RequestService, AlertService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'request-history',
    templateUrl: 'request-history.component.html'
})

export class RequestHistoryComponent implements OnInit {
    @Input()
    request: CitizenRequest;

    constructor(private requestService: RequestService, private alertService: AlertService) {}

    ngOnInit(): void {

    }

}
