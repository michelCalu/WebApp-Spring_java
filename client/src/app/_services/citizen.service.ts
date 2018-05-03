import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Citizen, Address } from '../_models/index';
import { User } from '../_models/index';
import { Observable } from 'rxjs';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';

// const httpOptions = {
//   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
// };

@Injectable()
export class CitizenService {

    serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }

    public getAbout() {
        return this.http.get('/about');
    }

    public createCitizen(citizen: Citizen): Observable<any> {
        console.log('CREATING CITIZEN : ' + JSON.stringify(citizen));
        return this.http.post(this.serverAddress + '/citizens', citizen);
    }
    // to test login

    public login(user: User) {
        return this.http.post(this.serverAddress + '/login', user);
    }

    public getCitizen(user: User): Observable<Citizen> {
        return this.http.get<Citizen>(this.serverAddress + `/citizens/${user.id}`)
            .catch(err => {
                this.messageService.error(this.translateService.instant('service.citizen.errorGetCitizen'));
                return Observable.of(null);
            });
    }

    public getNrnData(nrn: string): Observable<Object> {
        return this.http.get(this.serverAddress + `/nrn/${nrn}`)
            .catch(err => {
                this.messageService.error(this.translateService.instant('service.citizen.errorGetNrn'));
                return Observable.of(null);
            });
    }

    public getPendingCitizens( /*TODO should be filtered by commune*/): Observable<Citizen[]> {
        return this.http.get<Citizen[]>(this.serverAddress + '/citizens/pending' /* TODO should be citizens?status="pending"*/)
            .catch(err => {
                this.messageService.error(this.translateService.instant('service.citizen.errorGetCitizen'));
                return Observable.of(null);
            });
    }

}
