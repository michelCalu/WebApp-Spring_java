import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CitizenService } from '../_services/citizen.service';
import {Citizen} from "../_models/citizen.model";

@Component({
	templateUrl: './shownewcitizen.component.html',
})
export class ShowNewComponent implements OnInit {

	citizens: Citizen[];

	constructor(private router: Router, private citizenService: CitizenService) {

	}

	ngOnInit(): void {
		/*this.citizenService.showCitizen().subscribe((data:Citizen[]) => {
			this.citizens = data;
		});*/
	}
}
