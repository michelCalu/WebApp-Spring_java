import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService, AuthenticationService } from '../_services';

@Component({
    moduleId: module.id,
    templateUrl: './login.component.html',
})
export class LoginComponent {
    username: string;
    password: string;
    isEmployee = false;

    constructor(private router: Router, private authService: AuthenticationService, private alertService: AlertService) { }

    login(): void {
      const enriched = this.username + (this.isEmployee ? '_empl' : '_ctz') ;
        this.authService.login(enriched, this.password).subscribe(
        success => {
            if (success) {
                this.router.navigate(['myrequests']);
            } else {
                this.alertService.error('Nom d\'utilisateur ou mot de passe incorrect');
            }
        });
    }

}
