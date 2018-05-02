import { Component, Input } from '@angular/core';
import { Citizen } from '../_models';


@Component({
    selector: 'app-nrn-data',
    templateUrl: 'nrn-data.component.html'
})

export class NrnDataComponent {
    @Input()
    citizen: Citizen;
}
