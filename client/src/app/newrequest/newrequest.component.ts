import {Component, OnInit} from '@angular/core';
import {AuthenticationService, CitizenService, RequestTypeService} from '../_services';
import {RequestTypeModel} from "../_models";
import {Observable} from "rxjs/Observable";


@Component({
    moduleId: module.id,
    templateUrl: 'newrequest.component.html'
})

export class NewRequestComponent {
  municipalityRequestTypes: String[] = this.authService.getRequestTypes().map(requestType => requestType.description);

  constructor(private authService: AuthenticationService) { }

}
