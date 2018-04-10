import { Component } from '@angular/core';
import { AlertService, CitizenService } from "../_services";
import { Citizen } from "../_models/index";

@Component({
	moduleId: module.id,
	templateUrl: './createuser.component.html',
})
export class CreateUserComponent {

	citizen = new Citizen();

	constructor(private citizenService: CitizenService, private alertService: AlertService) { }

	createCitizen(): void {
		this.citizenService.createCitizen(this.citizen).subscribe(
			data => this.alertService.success('Enregistrement réussi'),
			error => this.alertService.error('Échec dans l\'enregistrement')
		);
	}

}
