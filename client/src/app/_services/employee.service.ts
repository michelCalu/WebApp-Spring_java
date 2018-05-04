import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Employee, Department } from '../_models/index';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class EmployeeService {

    serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }

    public createEmployee(employee: Employee): Observable<boolean> {
        return this.http.post<boolean>(/*this.serverAddress + */ '/employees', employee, { observe: 'response' }).
            map(resp => resp.status === 201)
            .catch(err => Observable.of(false));
    }

    public getDepartmentsByMunicipalityId(municipalityId: number): Observable<Department[]> {
        return this.http.get<Department[]>(/*this.serverAddress + */ '/departments?municipalityId=' + municipalityId)
            .catch(err => {
                this.messageService.error(this.translateService.instant('service.deparment.municipalityNotFound'));
                return [];
            });

    }
}
