import { Component, OnInit } from '@angular/core';

import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';

@Component({
    moduleId: module.id,
    templateUrl: 'newrequest.component.html'
})

export class NewRequestComponent implements OnInit {

    ngOnInit() {
        //this.loadAllUsers();
    }
}
