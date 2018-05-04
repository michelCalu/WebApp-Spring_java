import { Component, OnInit } from '@angular/core';

import { Citizen } from '../_models/citizen.model';
import { CitizenService } from '../_services/citizen.service';

@Component({
    moduleId: module.id,
    templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {
    /*currentUser: User;
    users: User[] = [];

    constructor(private user_services: User_services) {
        this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    }*/

    ngOnInit() {
        //this.loadAllUsers();
    }

    /*deleteUser(id: number) {
        this.user_services.delete(id).subscribe(() => { this.loadAllUsers() });
    }

    private loadAllUsers() {
        this.user_services.getAll().subscribe(users => { this.users = users; });
    }*/
}
