import { Component, OnInit, Input } from '@angular/core';
import { NationalityCertificateCreationComponent } from '../nationality-certificate-creation/nationality-certificate-creation.component';

@Component({
    selector: 'nationality-certificate-update',
    template: '<p>Il n\'y a pas de champs à modifier dans une demande de certificat de nationalité</p>'
})


export class NationalityCertificateUpdateComponent  extends NationalityCertificateCreationComponent {

}
