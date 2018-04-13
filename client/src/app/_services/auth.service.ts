import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { User } from '../_models';


@Injectable()
export class MockAuthService {

    constructor() {}
    private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    get isLoggedIn() {
        return this.loggedIn.asObservable();
    }

    getUser(): User {
        const stringifiedUser = localStorage.getItem('currentUser');
        return stringifiedUser ? JSON.parse(stringifiedUser) : undefined;
    }

    login(username: string, password: string): Observable<boolean> {
        return this._simulateLoginBackend(username, password).map(user => {
            if (user && user.token) {
                this.loggedIn.next(true);
                localStorage.setItem('currentUser', JSON.stringify(user));
                return true;
            } else {
                return false;
            }
        }) ;
    }

    logout(){
        this.loggedIn.next(false);
        localStorage.removeItem('currentUser');
    }

    private _simulateLoginBackend(username: string, password): Observable<any>{
        if (username === "root" && password === "root" ){
            const user = new User();
            user.name = 'John Doe';
            user.id = 23;
            user.token = 'a JWT token';
            return Observable.of(user);
        } else {
      // TODO this should return a HTTP response with error
      return Observable.of(null);
  }
}
}
