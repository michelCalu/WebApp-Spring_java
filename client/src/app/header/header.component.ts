import { Observable } from 'rxjs/Observable';
import { AlertService, AuthenticationService, CitizenService, EmployeeService } from './../_services/index';
import { LoginComponent } from './../login/index';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styles: [
    ]
})
export class HeaderComponent implements OnInit {

    currentLanguage: string;
    isLoggedIn$: Observable<boolean>;
    modalRef: BsModalRef;

    constructor(private authService: AuthenticationService, private alertService: AlertService,
        private translateService: TranslateService, private citizenService: CitizenService, private employeeService: EmployeeService,
        private modalService: BsModalService) { }

    ngOnInit() {
        this.isLoggedIn$ = this.authService.isLoggedIn;
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

    getUserInformation(): string {
        let result = '';
        const currentUser = this.authService.getCurrentUser();
        if (!currentUser || !currentUser['userData']) {
            return result;
        }
        const userData = currentUser['userData'];

        if (currentUser.type === 'employee') {
            result += (this.translateService.instant('employee') + ' ');
        }
        result += (userData['firstName'] + ' ' + userData['lastName'] + ' ');

        if (currentUser['company'] && currentUser['company']['companyName']) {
            result += (this.translateService.instant('company') + ': ' + currentUser['company']['companyName'] + ' ');
        }
        return result;

    }

    login(){
        this.modalRef = this.modalService.show(LoginComponent);
    }

}
