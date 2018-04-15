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
        }
    }

    private prepareSave(): any {
        const input = new FormData();
        input.append('insurance_certificate', this.form.get('insurance_certificate').value);
        input.append('carMake', this.form.get('carMake').value);
        input.append('carModel', this.form.get('carModel').value);
        input.append('colour', this.form.get('colour').value);
        input.append('carRegistrationNumber', this.form.get('carRegistrationNumber').value);

        return input;
    }

    onSubmit() {
        const formModel = this.prepareSave();
        this.loading = true;
        // this.requestService.createRequestWithUpload('parking');
        // TODO  call createREquest
        // on callback :
        //     alert('done!');
        //     this.loading = false;

    }

    clearFile() {
        this.form.get('insurance_certificate').setValue(null);
        this.fileInput.nativeElement.value = '';
    }
}
