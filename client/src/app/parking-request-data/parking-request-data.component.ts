import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest  } from '../_models';

@Component({
    selector: 'parking-request-data',
    templateUrl: 'parking-request-data.component.html'
})

export class ParkingRequestDataComponent implements OnInit {

    @Input()
    request: CitizenRequest;

    carData = new Map<string, Object>();

    constructor() { }

    ngOnInit(): void {
        for (const field of this.request.data) {
            this.carData[field.code] = field.fieldValue;
        }
    }

}
