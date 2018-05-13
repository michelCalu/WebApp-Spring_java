import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';
import { Company } from '../_models';


@Injectable()
export class CompanyService {

    // serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }

    public getPendingCompanies(municipalityId: number): Observable<Company[]> {
        return this.http.get<Company[]>('/companies/pending?municipalityID=' + municipalityId)
                        .map(res => {
                            console.log(res);
                            return res;
                        });
    }
}
