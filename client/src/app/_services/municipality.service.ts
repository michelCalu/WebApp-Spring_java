import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Municipality, Department } from '../_models/index';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class MunicipalityService {

    serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }


    public getDepartmentsByMunicipalityId(municipalityId: number): Observable<Department[]> {
        return this.http.get<Department[]>(/*this.serverAddress + */ '/departments?municipalityId=' + municipalityId)
            .catch(err => {
                this.messageService.error(this.translateService.instant('service.department.municipalityNotFound'));
                return [];
            });
    }

    public getMunicipalities(): Observable<Municipality[]> {
        return this.http.get<Municipality[]>(/*this.serverAddress + */ '/municipalities')
            .catch(err => {
                this.messageService.error(this.translateService.instant('service.department.municipalityNotFound'));
                return [];
            });
    }

    public getMunicipalityByMunicipalityName(name: String): Observable<Municipality[]> {
      console.log("in service " + name)
        return this.http.get<Municipality>(/*this.serverAddress + */ '/municipalities?name=' + name)
            .catch(err => {
            this.messageService.error(this.translateService.instant('service.department.municipalityNotFound'));
            return [];
        });
    }
}
