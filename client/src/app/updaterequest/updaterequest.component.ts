import { Component } from '@angular/core';
import { AuthenticationService } from '../_services';


@Component({
    templateUrl: 'updaterequest.component.html'
})

export class UpdateRequestComponent {
    constructor(private authService: AuthenticationService) { }

}
