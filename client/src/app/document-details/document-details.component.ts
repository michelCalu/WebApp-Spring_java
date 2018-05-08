import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest, Citizen, RequestDocument } from '../_models';
import { CitizenService, DocumentService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'document-details',
    templateUrl: 'document-details.component.html'
})

export class DocumentDetailsComponent implements OnInit {

    @Input()
    request: CitizenRequest;

    documents$: Observable<RequestDocument[]>;

    constructor(private documentService: DocumentService) { }

    ngOnInit(): void {
        this.documents$ = this.documentService.getRequestDocuments(this.request.id);
    }

}
