import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService, MockAuthService } from "../_services";

@Component({
	moduleId: module.id,
	templateUrl: './login.component.html',
})
export class LoginComponent{

	username: string;
	password: string;

	constructor(private router: Router, private authService: MockAuthService, private alertService: AlertService) {	}

	login(): void {
		this.authService.login(this.username, this.password).subscribe(success => {
			if (success) {
				this.router.navigate(['/']);
			} else {
				this.alertService.error('Nom d\'utilisateur ou mot de passe incorrecte');
			}
		});
	}

}
