import { Component, OnInit } from '@angular/core';

import { Company } from '../_models/company.model';
import { CompanyService } from '../_services/company.service';

@Component({
    moduleId: module.id,
    templateUrl: 'mycompanies.component.html'
})

export class MyCompaniesComponent implements OnInit {

    ngOnInit() {
        //this.loadAllUsers();
    }
}
