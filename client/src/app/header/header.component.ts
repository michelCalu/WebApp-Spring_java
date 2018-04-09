import { Observable } from 'rxjs/Observable';
import { AlertService, MockAuthService } from './../_services/index';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styles: [
    `.angular-logo {
        margin: 0 4px 3px 0;
        height: 35px;
        vertical-align: middle;
    }
    .fill-remaining-space {
        flex: 1 1 auto;
    }
    .disabled {
        pointer-events:none; //This makes it not clickable
        opacity:0.6;         //This grays it out to look disabled
    }
    `
    ]
})
export class HeaderComponent implements OnInit {

    isLoggedIn$: Observable<boolean>;

    constructor(private mockAuthService: MockAuthService, private alertService: AlertService) { }

    ngOnInit() {
        this.isLoggedIn$ = this.mockAuthService.isLoggedIn;
    }

    onLogout(){
        this.mockAuthService.logout();
        this.alertService.success('Vous êtes déconnecté');
    }

}
