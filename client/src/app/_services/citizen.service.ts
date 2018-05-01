import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Citizen, Address } from '../_models/index';
import { User } from '../_models/index';
import { Observable } from 'rxjs';
import * as configData from '../configuration-data';

// const httpOptions = {
//   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
// };

@Injectable()
export class CitizenService {

    serverAddress = configData.serverAddress;

  constructor(private http: HttpClient) {
  }

  public getAbout() {
    return this.http.get('/about');
  }

  public createCitizen(citizen: Citizen): Observable<any> {
    console.log('CREATING CITIZEN : ' + JSON.stringify(citizen));
    return this.http.post(this.serverAddress + '/citizens', citizen);
  }
// to test login

  public login(user: User) {
    return this.http.post(this.serverAddress + '/login', user);
  }

  public getCitizen(user: User): Observable<Citizen> {
    return this.http.get<Citizen>(this.serverAddress + `/citizens/${user.id}`);
  }

 /*
  public showFolders(){
    return this.http.get("/myfolders");
  }

  public showProfile() {
    return this.http.get("/myprofile");
  }

  public showRequests() {
    return this.http.get("/myrequests");
  }
*/
  /*public newRequest(){
    return this.http.post("/newrequest", request);
  }*/
}
