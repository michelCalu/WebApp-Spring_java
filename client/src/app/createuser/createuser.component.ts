import { Component, OnInit } from '@angular/core';
import { AlertService, CitizenService, MunicipalityService } from '../_services';
import { Citizen, Municipality } from '../_models/index';
import { Observable } from 'rxjs/Observable';

@Component({
    moduleId: module.id,
    templateUrl: './createuser.component.html',
})
export class CreateUserComponent implements OnInit {

    municipalities$: Observable<Municipality[]>;
    citizen = new Citizen();

    constructor(private citizenService: CitizenService, private alertService: AlertService,
        private municipalityService: MunicipalityService) { }

    ngOnInit() {
        this.municipalities$ = this.municipalityService.getMunicipalities();

        this.citizen.address.country = 'Belgique';
    }

    createCitizen(): void {
        this.citizenService.createCitizen(this.citizen).subscribe(
            data => this.alertService.success('Enregistrement réussi'),
            error => this.alertService.error('Échec dans l\'enregistrement')
        );
    }

}
