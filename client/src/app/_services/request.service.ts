import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { CitizenRequest, Citizen } from '../_models';
import { HttpHeaders } from '@angular/common/http';


@Injectable()
export class RequestService {

  constructor(private http: HttpClient) {
  }

  public createRequest(citizenId: number, requestType: string): Observable<boolean> {
    const requestBody = new CreateRequest();
    requestBody.citizen = citizenId;
    requestBody.type = requestType;
    return this.http.post<boolean>('/requests', requestBody, {observe: 'response'}).map(
     resp =>  resp.headers.has('Location'));
  }

  public createRequestWithFileUploads(request: CitizenRequest, files: Array<any> /* file ? */): Observable<boolean> {
      /*
        TODO should be: this.http.post('/requests', {request: request, files: files});
      */

    // TODO to be removed:
    let existingRequests = JSON.parse(sessionStorage.getItem('requests'));
    if (!existingRequests) {
      existingRequests = [];
    }

    request.status = 'ongoing';
    request.citizen = new Citizen();
    request.citizen.firstName = 'John';
    request.citizen.lastName = 'Doe';
    existingRequests.push(request);

    sessionStorage.setItem('requests', JSON.stringify(existingRequests));
    return Observable.of(true);

  }

  getCitizenRequests(citizenID: number): Observable<CitizenRequest[]> {
    // TODO should be: return this.http.get('/requests/citizenID?'+ citizenID);

    // TODO remove this
    const stringifiedCitizenRequests = sessionStorage.getItem('requests');
    const citizenRequests = stringifiedCitizenRequests ? JSON.parse(stringifiedCitizenRequests) : [];
    return Observable.of(citizenRequests);
    
  }
}

class CreateRequest {
  citizen: number;
  type: string;
}
