import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../_guards/index';
import { PeopleService } from '../_services/people.service';
import { User } from "../_models/user.model";

@Component({
	moduleId: module.id,
	templateUrl: './login.component.html',
})
export class LoginComponent{

	user: User = new User();

	constructor(private router: Router, private authService: AuthService, private peopleService: PeopleService) {

	}

	login(): void {
		this.authService.login(this.user);
		console.log("logged");
		/*this.peopleService.login(this.user).subscribe(data => {
			console.log("user connected");
		});*/
	}

}
