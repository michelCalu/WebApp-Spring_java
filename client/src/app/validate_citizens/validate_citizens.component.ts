import { Component, OnInit } from '@angular/core';
import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';
import { Observable } from 'rxjs/Observable';


@Component({
    templateUrl: 'validate_citizens.component.html'
})

export class ValidateCitizensComponent implements OnInit {

    pendingCitizens$: Observable<Citizen[]>;
    selectedCitizen: Citizen;

        constructor(private citizenService: CitizenService) { }

    ngOnInit() {
        this.pendingCitizens$ = this.citizenService.getPendingCitizens();
    }

    validate(citizen: Citizen) {
        this.citizenService.validateCitizenAccount(citizen).subscribe(success => {
            if (success) {
                this.pendingCitizens$ = this.citizenService.getPendingCitizens();
            }
        });
    }
}
