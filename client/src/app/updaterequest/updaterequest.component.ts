import { Component, OnInit } from '@angular/core';
import { AuthenticationService, RequestService } from '../_services';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { CitizenRequest } from '../_models/citizen-request.model';

@Component({
    templateUrl: 'updaterequest.component.html'
})

export class UpdateRequestComponent implements OnInit {

    request: CitizenRequest;

    constructor(private route: ActivatedRoute, private requestService: RequestService) { }

    ngOnInit() {
        this.route.paramMap.flatMap( params => {
            const requestId = Number(params.get('requestId'));
            return this.requestService.getRequestById(requestId);
        }).subscribe(req => this.request = req);
    }

}
