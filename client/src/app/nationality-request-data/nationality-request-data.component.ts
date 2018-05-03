import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest, Citizen } from '../_models';
import { CitizenService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'nationality-request-data',
    templateUrl: 'nationality-request-data.component.html'
})

export class NationalityRequestDataComponent implements OnInit {

    @Input()
    request: CitizenRequest;
    @Input()
    citizen: Citizen;

    nrnData$: Observable<Object>;


    constructor(private citizenService: CitizenService) { }

    ngOnInit(): void {
        this.nrnData$ = this.citizenService.getNrnData(this.citizen.nationalRegisterNb);
    }
}
