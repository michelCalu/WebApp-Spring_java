import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { CitizenService } from '../_services/citizen.service';
import { Citizen, Address } from "../_models/index";

@Component({
	moduleId: module.id,
	templateUrl: './createuser.component.html',
})
export class CreateUserComponent {

	citizen: Citizen = new Citizen();
	address: Address = new Address();

	constructor(private router: Router, private citizenService: CitizenService) {

	}

	createCitizen(): void {
		this.citizenService.createCitizen(this.citizen, this.address).subscribe(data => {
			console.log("user created");
		});
	}

}
