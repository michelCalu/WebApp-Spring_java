import { Observable } from 'rxjs/Observable';
import { AlertService, AuthenticationService } from './../_services/index';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styles: [
    ]
})
export class HeaderComponent implements OnInit {

    // TODO this should be done once, somewhere, for the whole application
    // language and fallback language
    currentLanguage: string;
    isLoggedIn$: Observable<boolean>;

    constructor(private authService: AuthenticationService, private alertService: AlertService,
        private translateService: TranslateService) { }

    ngOnInit() {
        this.isLoggedIn$ = this.authService.isLoggedIn;
        // TODO this should be done once, somewhere, for the whole application
        // language and fallback language
        this.translateService.setDefaultLang('fr');
        this.translateService.use('fr');
        this.currentLanguage = this.translateService.currentLang;
    }

    onLogout() {
        this.authService.logout();
        this.alertService.success('Vous êtes déconnecté');
    }

    isCitizen(): boolean {
        return this.authService.getCurrentUser().type === 'citizen';
    }

    isEmployee(): boolean {
        return this.authService.getCurrentUser().type === 'employee';
    }
  
    isAdministrator(): boolean {
       return this.authService.getCurrentUser().roles.includes('ADMIN');
    }

    switchLanguage() {
        const newLanguage = this.currentLanguage === 'fr' ? 'en' : 'fr';
        this.translateService.use(newLanguage);
        this.currentLanguage = newLanguage;
    }

}
