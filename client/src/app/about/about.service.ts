import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AboutService {

  constructor(private http: HttpClient) {}

  private aboutUrl = 'http://localhost:8080/about';

  public getAbout() {
    console.log('about sex');
    console.log(this.http.get(this.aboutUrl));
    return this.http.get(this.aboutUrl);
  }

}
