import { Component, OnInit } from '@angular/core';

import { People } from '../_models/people.Model';
import { PeopleService } from '../_services/people.Service';

@Component({
    moduleId: module.id,
    templateUrl: 'myrequests.component.html'
})

export class MyRequestsComponent implements OnInit {

    ngOnInit() {
        //this.loadAllUsers();
    }
}