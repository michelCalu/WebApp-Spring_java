import { Component, OnInit } from '@angular/core';

import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';

@Component({
    moduleId: module.id,
    templateUrl: 'myprofile.component.html'
})

export class MyProfileComponent implements OnInit {

    ngOnInit() {
        //this.loadAllUsers();
    }
}
