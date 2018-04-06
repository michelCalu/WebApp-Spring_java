import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService, CitizenService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'register.component.html'
})

export class RegisterComponent {
    _models: any = {};
    loading = false;

    constructor(
        private router: Router,
        private citizenService: CitizenService,
        private alertService: AlertService) { }

    register() {
        this.loading = true;
        /*this.citizen_services.create(this._models)
            .subscribe(
                data => {
                    this.alert_services.success('Registration successful', true);
                    this.router.navigate(['login']);
                },
                error => {
                    this.alert_services.error(error);
                    this.loading = false;
                });*/
    }
}
