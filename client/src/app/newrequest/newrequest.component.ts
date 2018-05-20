import { Component } from '@angular/core';
import { AuthenticationService } from '../_services';


@Component({
    moduleId: module.id,
    templateUrl: 'newrequest.component.html'
})

export class NewRequestComponent {
    constructor(private authService: AuthenticationService) { }

}
