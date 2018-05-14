import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';
import { Company, Mandatary } from '../_models';


@Injectable()
export class CompanyService {

    // serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }

    public createCompany(company: Company): Observable<any> {
        console.log('CREATING COMPANY : ' + JSON.stringify(company));
        return this.http.post(/*this.serverAddress + */ '/companies', company);
    }

    public createMandatary(company: Company): Observable<any>{
        var mandatary = new Mandatary();
        mandatary.citizen.id = company.contactPerson;
        mandatary.company.companyNb = company.companyNb;
        mandatary.role = 'owner';
        console.log("coucou");
        console.log(JSON.stringify(mandatary));
        return this.http.post('/mandataries', mandatary);
    }

    public getMandataries(citizenID: number): Observable<Mandatary[]> {
        return this.http.get<Mandatary[]>(/*this.serverAddress + */ '/mandataries?citizenId=' + citizenID)
            .map(res => {
                console.log(res);
                return res;
            })
            .catch(err => {
                this.messageService.error(this.translateService.instant('request.service.getError'));
                return [];
            });
    }

    public getPendingCompanies(municipalityId: number): Observable<Company[]> {
        return this.http.get<Company[]>('/companies/pending?municipalityID=' + municipalityId)
            .map(res => {
                console.log(res);
                return res;
            });
    }

    validateCompany(companyNumber: string): any {
        const body = {
            companyNb: companyNumber,
            companyStatus: 'active'
        };
        return this.http.patch(/*this.serverAddress + */ `/companies/${companyNumber}`, body)
        .map(res => true)
        .catch(err => {
            this.messageService.error(this.translateService.instant('service.company.errorValidateCompany'));
            return Observable.of(false);
        });
    }

}
