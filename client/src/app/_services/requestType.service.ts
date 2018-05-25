import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';

import { RequestTypeModel } from '../_models'


@Injectable()
export class RequestTypeService {

  constructor(private http: HttpClient, private messageService: AlertService, private translateService: TranslateService) { }

  public getMunicipalityRequestTypes(municipalityName: string): Observable<RequestTypeModel[]> {
    return this.http.get<RequestTypeModel[]>('/request-types?municipalityName=' + municipalityName)
      .map(res => {
        console.log(res);
        return res;
      });
  }

}
