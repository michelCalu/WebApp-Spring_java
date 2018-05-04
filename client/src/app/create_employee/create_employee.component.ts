import { Component, OnInit } from '@angular/core';
import { Employee, Municipality, Department } from '../_models';
import { AlertService, EmployeeService, MunicipalityService } from '../_services';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

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
        private alertService: AlertService, private router: Router) { }

    ngOnInit() {

        // TODO replace by actual call
        // const mockMunicipality = new Municipality();
        // mockMunicipality.municipalityID = 1;
        // mockMunicipality.name = 'mockMunicipality';
        // const mockMunicipalityBis = new Municipality();
        // mockMunicipalityBis.municipalityID = 2;
        // mockMunicipalityBis.name = 'no utilizar';
        // this.municipalities$ = Observable.of([mockMunicipality, mockMunicipalityBis]);

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
}
