import { Component, OnInit } from '@angular/core';
import { AlertService, CompanyService, MunicipalityService, AuthenticationService } from '../_services';
import { Company, Municipality } from '../_models/index';
import { Observable } from 'rxjs/Observable';

@Component({
    moduleId: module.id,
    templateUrl: 'createcompany.component.html',
})
export class CreateCompanyComponent implements OnInit {

    municipalities$: Observable<Municipality[]>;
    company = new Company();

    constructor(private companyService: CompanyService, private alertService: AlertService,
                private authService: AuthenticationService,
                private municipalityService: MunicipalityService) { }

    ngOnInit() {
        this.municipalities$ = this.municipalityService.getMunicipalities();

        this.company.contactPerson = this.authService.getCurrentUser().id;
        this.company.status = 'created';
    }

    createCompany(): void {
        this.companyService.createCompany(this.company, this.authService.getCurrentUser().id).subscribe( success => {
                if (success) {
                    this.alertService.success('Enregistrement réussi');
                } else {
                    this.alertService.error('Échec dans l\'enregistrement');
                }
            });

    }

    scrollUp() {
        console.log("onActivate");
        window.scroll(0,0);
    }

}
