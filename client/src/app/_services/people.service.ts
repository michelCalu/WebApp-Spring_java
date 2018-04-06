import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { People } from '../_models/index'
import { User } from '../_models/index'
import { Request } from

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class PeopleService {

  constructor(private http: HttpClient) {
  }

  //private serverUrl = 'http://localhost:8080/';

  public getAbout() {
    return this.http.get("/about");
  }

  public createPeople(people: People) {
    return this.http.post("/createpeople", people);
  }

  public login(user: User){
    return this.http.post("/login", user);
  }

  public showPeople(){
    return this.http.get("/showpeople");
  }

  public showFolders(){
    return this.http.get("/myfolders");
  }

  public showProfile(){
    return this.http.get("/myprofile");
  }

  public showRequests(){
    return this.http.get("/myrequests");
  }

  public newRequest(){
    return this.http.post("/newrequest", request);
  }
}
