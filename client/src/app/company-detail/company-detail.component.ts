import { Component, Input, OnInit } from '@angular/core';
import { Company, Citizen } from '../_models';
import { CitizenService } from '../_services/citizen.service';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'company-detail',
    templateUrl: 'company-detail.component.html'
})

export class CompanyDetailComponent implements OnInit {

    @Input()
    company: Company;

    contactPerson$: Observable<Citizen>;


    constructor(private citizenService: CitizenService) { }

    ngOnInit(): void {
        this.contactPerson$ = this.citizenService.getCitizenById(this.company.contactPerson);
    }


}
