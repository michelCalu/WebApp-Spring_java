import { Component, OnInit } from '@angular/core';
import { RequestService, AlertService, CitizenService, AuthenticationService } from '../_services';
import { CitizenRequest, Citizen } from '../_models';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
    moduleId: module.id,
    templateUrl: 'nationality-certificate-creation.component.html'
})

export class NationalityCertificateCreationComponent implements OnInit {

  requestor: Citizen;
  request = new CitizenRequest();

    constructor(
      private router: Router,
      private requestService: RequestService,
      private alertService: AlertService,
      private authService: AuthenticationService,
      private citizenService: CitizenService) {}

    ngOnInit() {
      const currentUser = this.authService.getCurrentUser();
      this.citizenService.getCitizen(currentUser).
        map(data => this.requestor = data).
        subscribe();
    }

    sendRequest() {
      console.log('CITZENID BEFORE REQUEST : ' + this.requestor.id);
      this.requestService.createRequest(this.requestor.id, 'nationalityCertificate').subscribe(success => {
          if (success) {
              this.router.navigate(['/myrequests']);
              this.alertService.success('Demande de certificat de nationalité bien envoyée');
          } else {
              this.alertService.error('Échec de la demande du certificat de nationalité');
          }
      });
    }

    cancelRequest() {
       this.router.navigate(['/myrequests']);
    }
}
