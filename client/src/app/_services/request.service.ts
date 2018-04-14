import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { CitizenRequest, Citizen } from '../_models';


@Injectable()
export class RequestService {

  constructor(private http: HttpClient) {
  }

  public createRequest(request: CitizenRequest): Observable<boolean> {
    // TODO should be: return this.http.post('/requests', request);

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
    const stringifiedCitizenRequests = localStorage.getItem('requests');
    const citizenRequests = stringifiedCitizenRequests ? JSON.parse(stringifiedCitizenRequests) : [];
    return Observable.of(citizenRequests);
    
  }
}
