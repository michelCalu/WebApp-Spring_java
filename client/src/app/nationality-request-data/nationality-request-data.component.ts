import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest, Citizen } from '../_models';
import { CitizenService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'nationality-request-data',
    templateUrl: 'nationality-request-data.component.html'
})

export class NationalityRequestDataComponent {

    @Input()
    request: CitizenRequest;
    @Input()
    citizen: Citizen;

}
