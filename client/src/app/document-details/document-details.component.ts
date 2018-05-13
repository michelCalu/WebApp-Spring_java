import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CitizenRequest, Citizen, RequestDocument } from '../_models';
import { CitizenService, DocumentService } from '../_services';
import { Observable } from 'rxjs/Observable';



@Component({
    selector: 'document-details',
    templateUrl: 'document-details.component.html'
})

export class DocumentDetailsComponent implements OnChanges {

    @Input()
    request: CitizenRequest;

    documents$: Observable<RequestDocument[]>;

    constructor(private documentService: DocumentService) { }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['request']) {
            this.documents$ = this.documentService.getRequestDocuments(this.request.id);
        }
    }

    downloadDocument(documentId: number): void {
        // TODO use actual name of the type of document
        this.documentService.downloadDocumentById(documentId, 'mockName').subscribe();
    }

}
