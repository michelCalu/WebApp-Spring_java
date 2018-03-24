import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';


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

  public createPeople(people) {
    return this.http.post("/createpeople", people);
  }

  public showPeople(){
    return this.http.get("/showpeople");
  }

}
