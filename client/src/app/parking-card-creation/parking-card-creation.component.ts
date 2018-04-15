import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { RequestService, AlertService } from '../_services';
import { CitizenRequest } from '../_models';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
    moduleId: module.id,
    templateUrl: 'parking-card-creation.component.html'
})


/* file handling inspired by https://nehalist.io/uploading-files-in-angular2/ */
export class ParkingCardCreationComponent implements OnInit {

    fileLoaded: boolean;
    form: FormGroup;
    loading = false;

    @ViewChild('fileInput') fileInput: ElementRef;

    constructor(private router: Router, private requestService: RequestService,
        private alertService: AlertService, private fb: FormBuilder) { }

    ngOnInit() {
        this.form = this.fb.group({
            carMake: ['', Validators.required],
            carModel: ['', Validators.required],
            colour: ['', Validators.required],
            carRegistrationNumber: ['', Validators.required],
            insurance_certificate: null
        });

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
        request.type =  'parkingCard';
        request.data = {
            carMake : this.form.get('carMake').value,
            carModel: this.form.get('carModel').value,
            colour: this.form.get('colour').value,
            carRegistrationNumber: this.form.get('carRegistrationNumber').value
        };

        this.requestService.createRequestWithFileUploads(request, [this.form.get('insurance_certificate').value]).subscribe(success => {
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
