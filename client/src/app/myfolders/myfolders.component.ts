import { Component, OnInit } from '@angular/core';

import { People } from '../_models/people.Model';
import { PeopleService } from '../_services/people.Service';

@Component({
    moduleId: module.id,
    templateUrl: 'myfolders.component.html'
})

export class MyFolderComponent implements OnInit {

    ngOnInit() {
        //this.loadAllUsers();
    }
}