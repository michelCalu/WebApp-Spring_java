import { Component, Input } from '@angular/core';
import { CitizenRequest } from '../_models';


@Component({
    selector: 'nationality-request-data',
    templateUrl: 'nationality-request-data.component.html'
})

export class NationalityRequestDataComponent {

    @Input()
    request: CitizenRequest;
}
