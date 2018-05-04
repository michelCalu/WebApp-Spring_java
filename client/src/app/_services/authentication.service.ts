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

@Injectable()
export class AuthenticationService {

    serverAddress = configData.serverAddress;

    private loggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.getCurrentUser() ? true : false);

    get isLoggedIn() {
        return this.loggedIn$.asObservable();
    }

    constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }

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

    logout() {
        this.loggedIn$.next(false);
        // remove user from local storage to log user out
        sessionStorage.removeItem('currentUser');
    }

    getCurrentUser(): User {
        return JSON.parse(sessionStorage.getItem('currentUser'));
    }
}
