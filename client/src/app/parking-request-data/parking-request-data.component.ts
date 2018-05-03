import { Component, Input } from '@angular/core';
import { CitizenRequest  } from '../_models';

@Component({
    selector: 'parking-request-data',
    templateUrl: 'parking-request-data.component.html'
})

export class ParkingRequestDataComponent {

    @Input()
    request: CitizenRequest;

    constructor() { }

}
