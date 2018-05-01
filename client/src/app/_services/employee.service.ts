import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Employee } from '../_models/index';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import * as configData from '../configuration-data';

@Injectable()
export class EmployeeService {

    serverAddress = configData.serverAddress;

    constructor(private http: HttpClient) {
    }

    public createEmployee(employee: Employee): Observable<boolean> {
        return this.http.post<boolean>(this.serverAddress + '/employees', employee, { observe: 'response' }).
            map(resp => resp.headers.has('Location'))
            .catch(err => Observable.of(false));
    }
}
