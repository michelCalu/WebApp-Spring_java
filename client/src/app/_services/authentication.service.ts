import { AuthenticationRequest } from '../_models/authentication.request';
import { User } from '../_models/user.model';
import { Injectable } from '@angular/core';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {
  
  serverAddress = configData.serverAddress;

  private loggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.getCurrentUser() ? true : false);

    get isLoggedIn() {
        return this.loggedIn$.asObservable();
    }
  
  constructor(private http: HttpClient) { }

    login(username: string, password: string): Observable<boolean> {
     const requestBody = new AuthenticationRequest();
      requestBody.password = password;
      requestBody.username = username;
      
      return this.http.post<User>('/auth', requestBody)
            .map(user => {
                // login successful if there's a jwt token in the response
                if (user && user.token) {
                    this.loggedIn$.next(true);
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                  return true;
                } else
                return false;
            });
    }

    logout() {
      this.loggedIn$.next(false);
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
  
  getCurrentUser(): User {
    return JSON.parse(localStorage.getItem('currentUser'));
  }
}