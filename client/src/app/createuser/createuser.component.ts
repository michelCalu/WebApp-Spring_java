import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { PeopleService } from '../_services/people.service';
import { People } from "../_models/people.model";

@Component({
	moduleId: module.id,
	templateUrl: './createuser.component.html',
})
export class CreateUserComponent{

	people: People = new People();

	constructor(private router: Router, private peopleService: PeopleService) {

	}

	createPeople(): void {
		this.peopleService.createPeople(this.people).subscribe(data => {
			console.log("user created");
		});
	}

}
