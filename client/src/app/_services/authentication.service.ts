import { AuthenticationRequest } from '../_models/authentication.request';
import { User } from '../_models/user.model';
import { Injectable } from '@angular/core';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { TranslateService } from '@ngx-translate/core';
import { Company } from '../_models/company.model';
import { CitizenService } from './citizen.service';
import { EmployeeService } from './employee.service';
import {Municipality, RequestTypeModel} from "../_models";
import {MunicipalityService} from "./municipality.service";
import {RequestTypeService} from "./requestType.service";

@Injectable()
export class AuthenticationService {

    serverAddress = configData.serverAddress;

    private loggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.getCurrentUser() ? true : false);

    get isLoggedIn() {
        return this.loggedIn$.asObservable();
    }

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService,
                private citizenService: CitizenService, private employeeService: EmployeeService,
                private municipalityService: MunicipalityService, private requestTypeService: RequestTypeService) { }

    login(username: string, password: string, type: string): Observable<boolean> {
        const enriched = username + (type === 'employee' ? '_empl' : '_ctz');
        const requestBody = new AuthenticationRequest();
        requestBody.password = password;
        requestBody.username = enriched;

        return this.http.post<User>(/*this.serverAddress + */ '/auth', requestBody)
            .map(user => {
                // login successful if there's a jwt token in the response
                if (user && user.token) {
                    this.loggedIn$.next(true);
                    user.type = type;
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    sessionStorage.setItem('currentUser', JSON.stringify(user));
                    this.addUserInfo(user.type, user.id);
                    return true;
                } else {
                    return false;
                }
            })
            .catch(err => {
                this.messageService.error(this.translateService.instant('authentication.service.getError'));
                return Observable.of(false);
            });

    }

    private addUserInfo (userType: string, userId: number): void {
        let userData$;
        if (userType === 'citizen') {
            userData$ = this.citizenService.getCitizenById(userId);
        } else if (userType === 'employee') {
            userData$ = this.employeeService.getEmployeeById(userId);
        }
        userData$.subscribe(userData => {
            const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
            currentUser['userData'] = userData;
            sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
            this.connectToMunicipality(userData.address.municipality);
        });
    }

    private connectToMunicipality (municipalityName: string): void {
        let municipalityData$ = this.municipalityService.getMunicipalityByMunicipalityName(municipalityName);
        municipalityData$.subscribe(municipalityData => {
            const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
            currentUser['municipality'] = municipalityData;
            sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
            this.addAvailableRequestTypes(municipalityName);
        })
    }

    private disconnectFromMunicipality (): void {
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        delete currentUser['municipality'];
        sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
        this.removeAvailableRequestTypes();
    }

    private addAvailableRequestTypes(municipalityName: string): void {
        let availableRequestTypes$ = this.requestTypeService.getMunicipalityRequestTypes(municipalityName);
        availableRequestTypes$.subscribe(availableRequestTypes => {
          const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
          currentUser['requestTypes'] = availableRequestTypes;
          sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
        })
    }

    private removeAvailableRequestTypes(): void {
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        delete currentUser['requestTypes'];
        sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
    }

    logout() {
        this.loggedIn$.next(false);
        // remove user from local storage to log user out
        sessionStorage.removeItem('currentUser');
    }

    connectToCompany(company: Company): void {
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        currentUser['company'] = company;
        sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
        this.disconnectFromMunicipality();
        this.connectToMunicipality(company.address.municipality);
    }

    disconnectFromCompany() {
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        delete currentUser['company'];
        sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
        this.disconnectFromMunicipality();
        this.connectToMunicipality(currentUser['userData'].address.municipality);
    }

    getCurrentCompany(): Company {
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        return currentUser['company'];
    }

    getCurrentUser(): User {
        return JSON.parse(sessionStorage.getItem('currentUser'));
    }

    getCurrentMunicipality(): Municipality {
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        return currentUser['municipality'];
    }

    getRequestTypes(): RequestTypeModel[] {
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        return currentUser['requestTypes'];
    }
}
