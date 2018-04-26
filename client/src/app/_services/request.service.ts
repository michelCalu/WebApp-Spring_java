import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import 'rxjs/add/operator/catch';
import { CitizenRequest, Citizen } from '../_models';
import { HttpHeaders } from '@angular/common/http';
import * as configData from '../configuration-data';
import { AlertService } from '.';
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
    return this.http.post<boolean>(this.serverAddress + '/requests', requestBody, {observe: 'response'}).map(
     resp =>  resp.headers.has('Location'));
  }

  public createRequestWithFileUploads(request: CitizenRequest, files: Array<any> /* file ? */): Observable<boolean> {

    return this.http.post(this.serverAddress + '/requests', {request: request, files: files})
            .map(res => true)
            .catch(err => Observable.of(false));

  }

  getCitizenRequests(citizenID: number): Observable<CitizenRequest[]> {
    // TODO should be: return this.http.get('/requests/citizenID?'+ citizenID);

    // TODO remove this
    const stringifiedCitizenRequests = sessionStorage.getItem('requests');
    const citizenRequests = stringifiedCitizenRequests ? JSON.parse(stringifiedCitizenRequests) : [];
    return Observable.of(citizenRequests);
  }

  getServiceRequests(serviceID: number): Observable<CitizenRequest[]> {
      return this.http
                // TODO should be:  .get(this.serverAddress + '/requests?serviceId= + serviceID */)
                // instead of:
                .get(this.serverAddress + '/requests?citizenId=1')
                .catch( err => {
                    this.messageService.error(this.translateService.instant('request.service.getError'));
                    return [];
                });

  }
}

class CreateRequest {
  citizen: number;
  type: string;
}
