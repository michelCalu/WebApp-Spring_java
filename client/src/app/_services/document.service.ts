import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs/Observable';
import { RequestDocument } from '../_models/document.model';

@Injectable()
export class DocumentService {

    constructor(private http: HttpClient, private messageService: AlertService,
        private translateService: TranslateService) {
    }


    getRequestDocuments(requestId: number): Observable<RequestDocument[]> {
        return this.http.get<RequestDocument[]>('/documents?requestId=' + requestId)
            // TODO this shall not be needed
            .map(res => {
                const result = [];
                res.forEach(documentId => result.push({id: documentId}));
                return result;
            })
            .catch(err => {
                this.messageService.error(this.translateService.instant('documents.service.getError'));
                return [];
            });
    }

}
