import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { CitizenRequest } from '../_models';


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
    existingRequests.push(request);

    sessionStorage.setItem('requests', JSON.stringify(existingRequests));
    return Observable.of(true);
  }
}
