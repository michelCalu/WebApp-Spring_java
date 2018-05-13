import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs/Observable';
import { RequestDocument } from '../_models/document.model';
import 'rxjs/add/observable/throw';
import * as FileSaver from 'file-saver';

@Injectable()
export class DocumentService {

    constructor(private http: HttpClient, private messageService: AlertService,
        private translateService: TranslateService) {
    }


    getRequestDocuments(requestId: number): Observable<RequestDocument[]> {
        return this.http.get<RequestDocument[]>('/documents?requestId=' + requestId)
            .catch(err => {
                this.messageService.error(this.translateService.instant('documents.service.getError'));
                return [];
            });
    }

    downloadDocumentById(documentId: number, fileName: string): Observable<any> {
        return this.http.get('/documents/' + documentId, {responseType: 'blob'})
        .map(res => {
            const fileBlob = res;
            const blob = new Blob([fileBlob], {
                type: 'application/pdf'
            });
            const url = window.URL.createObjectURL(new Blob([res], {type: 'application/pdf'}));
            const filename = fileName;
            FileSaver.saveAs(blob, filename);
        })
        .catch(err => {
            this.messageService.error(this.translateService.instant('documents.service.getError'));
            return Observable.throw(err);
        });
    }

}
