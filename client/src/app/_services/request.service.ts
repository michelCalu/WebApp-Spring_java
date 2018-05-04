import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/catch';
import { CitizenRequest, Citizen } from '../_models';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';


@Injectable()
export class RequestService {

    serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService,
        private translateService: TranslateService) {
    }


    public createRequestWithFileUploads(formData: FormData, requestType: String): Observable<boolean> {
        const header = new HttpHeaders({ 'enctype': 'multipart/form-data' });
        return this.http.post(/*this.serverAddress + */'/requests?requestType=' + requestType, formData, {headers: header})
            .map(res => true)
            .catch(err => Observable.of(false));

    }

    getCitizenRequests(citizenID: number): Observable<CitizenRequest[]> {
        return this.http.get<CitizenRequest[]>(/*this.serverAddress + */ '/requests?citizenId=' + citizenID)
            .catch(err => {
                this.messageService.error(this.translateService.instant('request.service.getError'));
                return [];
            });
    }

    getDepartmentRequests(departmentID: number): Observable<CitizenRequest[]> {
        return this.http
        .get(/*this.serverAddress + */ '/requests?departmentId=' + departmentID)
            .catch(err => {
                this.messageService.error(this.translateService.instant('request.service.getError'));
                return [];
            });

    }

    assignThisRequest(employeeId: number, request: CitizenRequest): Observable<boolean> {
        throw new Error("Method not implemented.");
    }

    performAction(request: CitizenRequest, action: string, aditionalData: Object) {
        throw new Error("Method not implemented.");
    }

}

class CreateRequest {
    citizen: number;
    type: string;
}
