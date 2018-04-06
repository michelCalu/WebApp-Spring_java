import { Component, OnInit } from '@angular/core';

import { People } from '../_models/people.Model';
import { PeopleService } from '../_services/people.Service';

@Component({
    moduleId: module.id,
    templateUrl: 'newrequest.component.html'
})

export class NewRequestComponent implements OnInit {

    ngOnInit() {
        //this.loadAllUsers();
    }
}