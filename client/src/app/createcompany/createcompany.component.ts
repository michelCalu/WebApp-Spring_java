import { Component, OnInit } from '@angular/core';
import { AlertService, CompanyService, MunicipalityService, AuthenticationService } from '../_services';
import { Company, Municipality } from '../_models/index';
import { Observable } from 'rxjs/Observable';

@Component({
    moduleId: module.id,
    templateUrl: './createCompany.component.html',
})
export class CreateCompanyComponent implements OnInit {

    municipalities$: Observable<Municipality[]>;
    company = new Company();

    constructor(private companyService: CompanyService, private alertService: AlertService,
                private authService: AuthenticationService,
                private municipalityService: MunicipalityService) { }

    ngOnInit() {
        this.municipalities$ = this.municipalityService.getMunicipalities();

        this.company.address.country = 'Belgique';
        this.company.contactPerson = this.authService.getCurrentUser().id;
        this.company.status = 'created';
    }

    createCompany(): void {
        this.companyService.createCompany(this.company).subscribe(
            data => this.alertService.success('Enregistrement réussi'),
            error => this.alertService.error('Échec dans l\'enregistrement')
        );
        this.companyService.createMandatary(this.company).subscribe(
            data => this.alertService.success('Enregistrement réussi'),
            error => this.alertService.error('Échec dans l\'enregistrement')
        );
    }

}
