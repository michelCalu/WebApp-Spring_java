import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { PeopleService } from '../service/people.service';
import {People} from "../model/people.model";

@Component({
	templateUrl: './shownewpeople.component.html',
})
export class ShowNewComponent implements OnInit {

	peoples: People[];

	constructor(private router: Router, private peopleService: PeopleService) {

	}

	ngOnInit(): void {
		this.peopleService.showPeople().subscribe((data:People[]) => {
			this.peoples = data;
		});
	}

}
