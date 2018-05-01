import { Component } from '@angular/core';
import { Employee } from '../_models';
import { AlertService, EmployeeService } from '../_services';
import { Router } from '@angular/router';

@Component({
    templateUrl: 'create_employee.component.html'
})

export class CreateEmployeeComponent {

    employee = new Employee();

    constructor(private employeeService: EmployeeService, private alertService: AlertService, private router: Router) { }

    createEmployee(): void {
        this.employeeService.createEmployee(this.employee).subscribe(success => {
            if (success) {
                this.alertService.success('Enregistrement réussi', true);
                this.router.navigate(['/create_employee']);
            } else {
                this.alertService.error('Échec dans l\'enregistrement', true);
                this.router.navigate(['/create_employee']);
            }
        });
    }
}
