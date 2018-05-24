import { Component, OnInit } from '@angular/core';
import { Employee, Municipality, Department, Address } from '../_models';
import { AlertService, EmployeeService, MunicipalityService } from '../_services';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

@Component({
    templateUrl: 'create_employee.component.html'
})

export class CreateEmployeeComponent implements OnInit {

    departments$: Observable<Department[]>;
    municipalities$: Observable<Municipality[]>;
    selectedMunicipalityId: number;
    selectedDepartmentId: number;
    employee = new Employee();

    constructor(private employeeService: EmployeeService, private municipalityService: MunicipalityService,
        private alertService: AlertService, private router: Router, private translateService: TranslateService) {
      this.employee.address.country = translateService.instant('belgium');
    }

    ngOnInit() {
        this.municipalities$ = this.municipalityService.getMunicipalities();
    }

    createEmployee(): void {
        this.employee.departmentIds.push(this.selectedDepartmentId);
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

    municipalityChange(municipalityID: number) {
        this.departments$ = this.municipalityService.getDepartmentsByMunicipalityId(municipalityID);
        this.selectedDepartmentId = null;
    }

    scrollUp() {
        console.log("onActivate");
        window.scroll(0,0);
    }
}
