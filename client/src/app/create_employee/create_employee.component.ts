import { Component } from '@angular/core';
import { Employee } from '../_models';
import { AlertService } from '../_services';

@Component({
    templateUrl: 'create_employee.component.html'
})

export class CreateEmployeeComponent {

    employee = new Employee();

	constructor(/*private citizenService: CitizenService,*/ private alertService: AlertService) { }

	createEmployee(): void {
        console.log(' truc');
		// this.citizenService.createCitizen(this.citizen).subscribe(
		// 	data => this.alertService.success('Enregistrement réussi'),
		// 	error => this.alertService.error('Échec dans l\'enregistrement')
		// );
	}
}
