import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../_guards/index';
import { CitizenService } from '../_services/citizen.service';
import { User } from "../_models/user.model";

@Component({
	moduleId: module.id,
	templateUrl: './login.component.html',
})
export class LoginComponent{

	user: User = new User();

	constructor(private router: Router, private authService: AuthService, private citizenService: CitizenService) {

	}

	login(): void {
		this.authService.login(this.user);
		console.log("logged");
		/*this.citizenService.login(this.user).subscribe(data => {
			console.log("user connected");
		});*/
	}

}
