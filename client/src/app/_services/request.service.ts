import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/catch';
import { CitizenRequest, Citizen, RequestEvent } from '../_models';
import * as configData from '../configuration-data';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';
import * as FileSaver from 'file-saver';


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
        const body = {id: request.id, assignee: { id: employeeId}};
        return this.http.patch('/requests', body)
            .map(res => true )
            .catch(error => {
                this.messageService.error(this.translateService.instant('request.service.assignError'));
                return Observable.of(false);
            });
    }

    performAction(request: CitizenRequest, action: string, additionalData: Object): Observable<Boolean> {
      console.log('Performing action ' + action + 'on request : ' + JSON.stringify(request));
      if (action === 'accept') {
        return this.http.patch('/requests', {'id':request.id, 'status': {'id': 5, 'name': 'accepted'}})
          .map(res => true )
          .catch(error => Observable.of(false));
      } else if (action === 'reject') {
         return this.http.patch('/requests', {'id':request.id, 'status': {'id': 4, 'name': 'rejected'}})
           .map(res =>true).
           catch(err => Observable.of(false));
      } else{
        this.messageService.error('not implemented');
      }
    }

    getRequestById(requestId: number): Observable<CitizenRequest> {
        return this.http.get<CitizenRequest>(/*this.serverAddress + */ '/requests/' + requestId)
        .catch(err => {
            this.messageService.error(this.translateService.instant('request.service.getError'));
            return Observable.of(null);
        });
    }

    getRequestEvents(requestId: number): Observable<RequestEvent[]> {
        return this.http
            .get(/*this.serverAddress + */ '/events?requestId=' + requestId)
            .map( (events: RequestEvent[]) => {
                for (const event of events) {
                    const dateArray = event.at;
                    event.at = new Date(dateArray[0], dateArray[1], dateArray[2], dateArray[3], dateArray[4], dateArray[5]);
                }
                return events;
            })
            .catch(err => {
                this.messageService.error(this.translateService.instant('request.service.getEvents'));
                return [];
            });
    }

    downloadFileByCode(requestId: number, code: string, fileName: string, fileType: string): Observable<any> {
        return this.http.get('/requests/' + requestId + '/file?code=' + code, {responseType: 'blob'})
        .map(res => {
            const blob = new Blob([res], { type: fileType });
            const url = window.URL.createObjectURL(new Blob([res], { type: fileType }));
            FileSaver.saveAs(blob, fileName);
        })
        .catch(err => {
            this.messageService.error(this.translateService.instant('documents.service.getError'));
            return Observable.throw(err);
        });
    }
}

class CreateRequest {
    citizen: number;
    type: string;
}
