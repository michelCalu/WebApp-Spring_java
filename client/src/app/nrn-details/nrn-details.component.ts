import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest, Citizen } from '../_models';
import { CitizenService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'nrn-details',
    templateUrl: 'nrn-details.component.html'
})

export class NrnDetailsComponent implements OnInit {

    @Input()
    citizen: Citizen;

    nrnData$: Observable<Object>;


    constructor(private citizenService: CitizenService) { }

    ngOnInit(): void {
        this.nrnData$ = this.citizenService.getNrnData(this.citizen.nationalRegisterNb);
    }
}
