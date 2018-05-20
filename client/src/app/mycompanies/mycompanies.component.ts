import { Component, OnInit } from '@angular/core';

import { Company, Mandatary } from '../_models/index';
import { CompanyService } from '../_services/company.service';
import { MockAuthService } from '../_services';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
    templateUrl: 'mycompanies.component.html'
})

export class MyCompaniesComponent implements OnInit {
    mandataries$: Observable<Mandatary[]>;
    currentCompany: Company;

    constructor(private companyService: CompanyService, private authService: AuthenticationService) {}

    ngOnInit() {
        console.log(this.mandataries$);
        const currentUser = this.authService.getCurrentUser();
        this.mandataries$ = this.companyService.getMandataries(currentUser.id);
        this.currentCompany = this.authService.getCurrentCompany();
    }

    connectToCompany(company: Company) {
        this.authService.connectToCompany(company);
        this.currentCompany = company;
    }

    disconnectFromCompany() {
        this.authService.disconnectFromCompany();
        this.currentCompany = null;
    }
}
