import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/catch';
import { CitizenRequest, Citizen } from '../_models';
import { HttpHeaders } from '@angular/common/http';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';


@Injectable()
export class RequestService {

    serverAddress = configData.serverAddress;

    constructor(private http: HttpClient, private messageService: AlertService,
        private translateService: TranslateService) {
    }

    public createRequest(citizenId: number, requestType: string): Observable<boolean> {
        const requestBody = new CreateRequest();
        requestBody.citizen = citizenId;
        requestBody.type = requestType;
        return this.http.post<boolean>(this.serverAddress + '/requests', requestBody, { observe: 'response' }).map(
            resp => resp.headers.has('Location'));
    }

    public createRequestWithFileUploads(request: CitizenRequest/*, files: Array<any>*/): Observable<boolean> {
        return this.http.post(this.serverAddress + '/requests', /*{request: request, files: files}*/ request)
            .map(res => true)
            .catch(err => Observable.of(false));

    }

    getCitizenRequests(citizenID: number): Observable<CitizenRequest[]> {
        return this.http.get<CitizenRequest[]>(this.serverAddress + '/requests?citizenId=' + citizenID)
            .catch(err => {
                this.messageService.error(this.translateService.instant('request.service.getError'));
                return [];
            });
    }

    getServiceRequests(serviceID: number): Observable<CitizenRequest[]> {
        return this.http
            .get(this.serverAddress + '/requests?serviceId=' + serviceID)
            .catch(err => {
                this.messageService.error(this.translateService.instant('request.service.getError'));
                return [];
            });

    }
}

class CreateRequest {
    citizen: number;
    type: string;
}
