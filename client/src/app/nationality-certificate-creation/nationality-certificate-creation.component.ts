import { Component, OnInit } from '@angular/core';
import { RequestService, AlertService } from '../_services';
import { CitizenRequest } from '../_models';

@Component({
    moduleId: module.id,
    templateUrl: 'nationality-certificate-creation.component.html'
})

export class NationalityCertificateCreationComponent implements OnInit {

    constructor(private requestService: RequestService, private alertService: AlertService) { }

    ngOnInit() {
    }

    sendRequest() {
        const request = new CitizenRequest();
        request.type =  'nationalityCertificate';
        this.requestService.createRequest(request).subscribe(success => {
            if (success) {
                this.alertService.success("Demande de certificat de nationalité bien envoyé");
            } else {
                this.alertService.error("Échec dans la demande du certificat de nationalité");
            }
        });
    }
}
