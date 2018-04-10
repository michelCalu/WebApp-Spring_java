import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Citizen, Address } from '../_models/index'
import { User } from '../_models/index'
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class CitizenService {

  constructor(private http: HttpClient) {
  }

  //private serverUrl = 'http://localhost:8080/';
  public getAbout() {
    return this.http.get("/about");
  }

  public createCitizen(citizen: Citizen): Observable<any>{
    return this.http.post("/citizens", citizen);
  }
// to test login

  public login(user: User){
    return this.http.post("/login", user);
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
