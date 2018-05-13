import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Municipality, Department } from '../_models/index';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class DepartmentService {

    // serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }


    public getDepartment(departmentId: number): Observable<Department> {
        return this.http.get<Department>(/*this.serverAddress + */ '/departments/' + departmentId)
            .catch(err => {
                this.messageService.error(this.translateService.instant('service.department.departmentNotFound'));
                return [];
            });
    }

}
