import { Component, Input, OnInit, Output, EventEmitter, OnChanges, OnChanges, SimpleChanges } from '@angular/core';
import { CitizenRequest, Citizen, RequestEvent } from '../_models';
import { AuthenticationService, CitizenService, RequestService, AlertService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'request-history',
    templateUrl: 'request-history.component.html'
})

export class RequestHistoryComponent implements OnChanges {
    @Input()
    request: CitizenRequest;

    events$: Observable<RequestEvent[]>;

    constructor(private requestService: RequestService, private alertService: AlertService) {}

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['request']) {
            this.events$ = this.requestService.getRequestEvents(this.request.id).map(requests => {
                requests.sort((a: RequestEvent, b: RequestEvent) => b.at.getTime() - a.at.getTime());
                return requests;
            });
        }
    }

}
