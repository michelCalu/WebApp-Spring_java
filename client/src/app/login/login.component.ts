import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { CitizenService } from '../_services/citizen.service';
import { User } from "../_models/user.model";
import {AuthenticationService, MockAuthService} from "../_services";

@Component({
	moduleId: module.id,
	templateUrl: './login.component.html',
})
export class LoginComponent{

  username: string;
  password: string;

	constructor(private router: Router, private authService: MockAuthService, private citizenService: CitizenService) {	}

	login(): void {
		this.authService.login(this.username, this.password).subscribe(success => {
		  if (success) {
		    console.log('connected!');
        this.router.navigate(['/']);
      } else {
		    console.log('NOT connected');
      }
    });
	}

	logout(): void {
		this.authService.logout();
	}

}
