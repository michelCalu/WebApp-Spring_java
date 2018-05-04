import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import {RequestService, AlertService, CitizenService, AuthenticationService} from '../_services';
import {Citizen, CitizenRequest} from '../_models';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {RequestField} from "../_models/request-field.model";

@Component({
    moduleId: module.id,
    templateUrl: 'parking-card-creation.component.html'
})


/* file handling inspired by https://nehalist.io/uploading-files-in-angular2/ */
export class ParkingCardCreationComponent implements OnInit {

    fileLoaded: boolean;
    form: FormGroup;
    loading = false;
    requestor: Citizen;
    //TODO : Not sure about this ... but it may work
    carMake = new RequestField();
    carModel = new RequestField();
    carColour = new RequestField();
    carRegistrationNumber = new RequestField();
    greenCard = new RequestField();

    @ViewChild('fileInput') fileInput: ElementRef;

    constructor(
      private router: Router,
      private requestService: RequestService,
      private alertService: AlertService,
      private fb: FormBuilder,
      private authService: AuthenticationService,
      private citizenService: CitizenService) {

      this.carMake.code = 'citizenParkingCardCarMake';
      this.carMake.fieldType = 'String';
      this.carModel.code = 'citizenParkingCardCarModel';
      this.carModel.fieldType = 'String';
      this.carColour.code = 'citizenParkingCardCarColour';
      this.carColour.fieldType = 'String';
      this.carRegistrationNumber.code = 'citizenParkingCardPlateNumber';
      this.carRegistrationNumber.fieldType = 'String';
      this.greenCard.code = 'citizenParkingCardGreenCard';
      this.greenCard.fieldType = 'File';
    }

    ngOnInit() {
        this.form = this.fb.group({
            carMake: ['', Validators.required],
            carModel: ['', Validators.required],
            colour: ['', Validators.required],
            carRegistrationNumber: ['', Validators.required],
            insurance_certificate: null
        });
      const currentUser = this.authService.getCurrentUser();
      this.citizenService.getCitizen(currentUser).subscribe(
        data => this.requestor = data,
        err => this.alertService.error('Citoyen inconnu'));

    }


    onFileChange(event) {
        if (event.target.files.length > 0) {
            const file = event.target.files[0];
            this.form.get('insurance_certificate').setValue(file);
            this.fileLoaded = true;
        }
    }


    onSubmit() {
        this.loading = true;
        const request = new CitizenRequest();
        request.typeDescription = 'citizenParkingCard';
        request.citizen = this.requestor;

        this.carMake.fieldValue = this.form.get('carMake').value;
        this.carModel.fieldValue = this.form.get('carModel').value;
        this.carColour.fieldValue = this.form.get('colour').value;
        this.carRegistrationNumber.fieldValue = this.form.get('carRegistrationNumber').value;
        this.greenCard.fieldFile = this.form.get('insurance_certificate').value;

        request.data = [this.carMake, this.carModel, this.carColour, this.carRegistrationNumber/*, this.greenCard*/];

        this.requestService.createRequestWithFileUploads(request).subscribe(success => {
            this.loading = false;
            if (success) {
                this.router.navigate(['/myrequests']);
                this.alertService.success('Demande de carte de parking bien envoyé');
            } else {
                this.alertService.error('Échec dans la carte de parking');
            }
        });

    }

    clearFile() {
        this.form.get('insurance_certificate').setValue(null);
        this.fileInput.nativeElement.value = '';
        this.fileLoaded = false;
    }
}
