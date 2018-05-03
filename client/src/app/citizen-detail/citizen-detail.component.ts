import { Component, Input } from '@angular/core';
import { Citizen } from '../_models';


@Component({
    selector: 'citizen-detail',
    templateUrl: 'citizen-detail.component.html'
})

export class CitizenDetailComponent {
    @Input()
    citizen: Citizen;
}
