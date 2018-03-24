import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { PeopleService } from '../service/people.service';
import {People} from "../model/people.model";

@Component({
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
