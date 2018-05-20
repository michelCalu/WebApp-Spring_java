import { Component, OnInit } from '@angular/core';

import { Company, Mandatary } from '../_models/index';
import { CompanyService } from '../_services/company.service';
import { MockAuthService } from '../_services';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
    moduleId: module.id,
    templateUrl: 'mycompanies.component.html'
})

export class MyCompaniesComponent implements OnInit {
    //companies$: Observable<Company[]>;
    selectedRequest: Company;
    companiesCreationDates = new Map<number, Date>();
    mandataries$: Observable<Mandatary[]>;

    test: Observable<Date>;

    constructor(private companyService: CompanyService, private authService: AuthenticationService) {}

    ngOnInit() {
        console.log(this.mandataries$);
        const currentUser = this.authService.getCurrentUser();
        this.mandataries$ = this.companyService.getMandataries(currentUser.id);
        this.mandataries$.subscribe(requests => {
            for (const request of requests) {
                /*this.companyService.getCreationDate(request.id)
                        .subscribe(creationDate => this.companiesCreationDates[request.id] = creationDate);*/
                console.log(request);
            }
        });
        console.log(this.mandataries$);
    }
}
