import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { RequestService, AlertService, CitizenService, AuthenticationService } from '../_services';
import { Citizen, CitizenRequest, Company } from '../_models';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { RequestField } from "../_models/request-field.model";

@Component({
    templateUrl: 'parking-card-creation.component.html'
})


/* file handling inspired by https://nehalist.io/uploading-files-in-angular2/ */
export class ParkingCardCreationComponent implements OnInit {

    isCompany: boolean;
    isUpdate = false;

    insuranceFileLoaded = false;
    carUserProofLoaded = false;
    form: FormGroup;
    loading = false;
    requestor: Citizen;
    greenCardCode: string;
    userProofCode: string;


    @ViewChild('insuranceCertificateInput') insuranceCertificateInput: ElementRef;
    @ViewChild('userProofInput') userProofInput: ElementRef;

    constructor(
        protected router: Router,
        protected requestService: RequestService,
        protected alertService: AlertService,
        private fb: FormBuilder,
        private authService: AuthenticationService,
        private citizenService: CitizenService) { }

    ngOnInit() {
        this.isCompany = (this.authService.getCurrentCompany() != null);
        const validators = {
            carMake: ['', Validators.required],
            carModel: ['', Validators.required],
            colour: ['', Validators.required],
            carRegistrationNumber: ['', Validators.required],
            insurance_certificate: null,
            car_user_proof: null
        };
        if (this.isCompany) {
            validators['carContactPersonEmail'] = ['', Validators.required];
            validators['carVisitorFirstName'] = ['', Validators.required];
            validators['carVisitorLastName'] = ['', Validators.required];
        }

        this.form = this.fb.group(validators);
        const currentUser = this.authService.getCurrentUser();
        this.citizenService.getCitizen(currentUser).subscribe(
            data => this.requestor = data,
            err => this.alertService.error('Citoyen inconnu'));

        this.greenCardCode = this.isCompany ? 'companyParkingCardGreenCard' : 'citizenParkingCardGreenCard';
        this.userProofCode = this.isCompany ? 'companyParkingCardUserProof' : 'citizenParkingCardUserProof';

    }


    onFileChange(event: any, inputFieldName: string) {
        if (event.target.files.length > 0) {
            const file = event.target.files[0];
            if (inputFieldName === 'insuranceCertificate') {
                this.form.get('insurance_certificate').setValue(file);
                this.insuranceFileLoaded = true;
            } else {
                this.form.get('car_user_proof').setValue(file);
                this.carUserProofLoaded = true;
            }
        }
    }


    onSubmit() {
        this.loading = true;

        const request = this.extractRequest();

        const formData = new FormData();
        formData.append('request', new Blob([JSON.stringify(request)], { type: 'application/json' }));
        formData.append(this.greenCardCode, this.form.get('insurance_certificate').value);
        formData.append(this.userProofCode, this.form.get('car_user_proof').value);

        this.requestService.createRequestWithFileUploads(formData, this.isCompany ? 'companyParkingCard' : 'citizenParkingCard')
            .subscribe(success => {
                this.loading = false;
                if (success) {
                    this.router.navigate(['/myrequests']);
                    this.alertService.success('Demande de carte de parking bien envoyé');
                } else {
                    this.alertService.error('Échec dans la carte de parking');
                }
            });

    }

    clearFile(inputFieldName: string) {
        if (inputFieldName === 'insuranceCertificate') {
            this.form.get('insurance_certificate').setValue(null);
            this.insuranceCertificateInput.nativeElement.value = '';
            this.insuranceFileLoaded = false;
        } else {
            this.form.get('car_user_proof').setValue(null);
            this.userProofInput.nativeElement.value = '';
            this.carUserProofLoaded = false;
        }
    }

    extractRequest(): CitizenRequest {
        let request = new CitizenRequest();
        request.typeDescription = this.isCompany ? 'companyParkingCard' : 'citizenParkingCard';
        request.citizen = this.requestor;

        const carMake: RequestField = {
            code: this.isCompany ? 'companyParkingCardCarMake' : 'citizenParkingCardCarMake',
            fieldType: 'String',
            fieldValue: this.form.get('carMake').value
        };

        const carModel: RequestField = {
            code: this.isCompany ? 'companyParkingCardCarModel' : 'citizenParkingCardCarModel',
            fieldType: 'String',
            fieldValue: this.form.get('carModel').value
        };

        const carColour: RequestField = {
            code: this.isCompany ? 'companyParkingCardCarColour' : 'citizenParkingCardCarColour',
            fieldType: 'String',
            fieldValue: this.form.get('colour').value
        };

        const carRegistrationNumber: RequestField = {
            code: this.isCompany ? 'companyParkingCardPlateNumber' : 'citizenParkingCardPlateNumber',
            fieldType: 'String',
            fieldValue: this.form.get('carRegistrationNumber').value
        };

        request.data = [carMake, carModel, carColour, carRegistrationNumber];

        if (this.isCompany) {
            request = this.addSpecificCompanyData(request);
        }
        return request;

    }

    private addSpecificCompanyData(request: CitizenRequest): CitizenRequest {
        request.data.push({
            code: 'companyParkingCardContactPersonEmail',
            fieldType: 'String',
            fieldValue: this.form.get('carContactPersonEmail').value
        });

        request.data.push({
            code: 'companyParkingCardUserFirstName',
            fieldType: 'String',
            fieldValue: this.form.get('carVisitorFirstName').value
        });

        request.data.push({
            code: 'companyParkingCardUserLastName',
            fieldType: 'String',
            fieldValue: this.form.get('carVisitorLastName').value
        });
        if (!request.company) {
            request.company = new Company();
        }
        request.company.companyNb = this.authService.getCurrentCompany().companyNb;

        return request;
    }
}
