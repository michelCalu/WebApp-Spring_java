import { Component, OnInit, Input } from '@angular/core';
import { ParkingCardCreationComponent } from '../parking-card-creation';
import { CitizenRequest } from '../_models/citizen-request.model';
import { Router } from '@angular/router';
import { RequestService, AlertService, AuthenticationService, CitizenService } from '../_services';
import { FormBuilder } from '@angular/forms';

@Component({
    selector: 'parking-card-update',
    templateUrl: '../parking-card-creation/parking-card-creation.component.html'
})


export class ParkingCardUpdateComponent  extends ParkingCardCreationComponent implements OnInit {

    @Input()
    request: CitizenRequest;

    files = new Map <string, Object>();
    actionGreenCardFile = 'keep';
    actionUserProofFile = 'keep';
    actions =  ['keep', 'delete', 'replace'];

    readonly fieldCodeToFormFieldName = {
        citizenParkingCardCarMake : 'carMake',
        companyParkingCardCarMake : 'carMake',
        citizenParkingCardCarModel : 'carModel',
        companyParkingCardCarModel : 'carModel',
        citizenParkingCardCarColour: 'colour',
        companyParkingCardCarColour: 'colour',
        citizenParkingCardPlateNumber: 'carRegistrationNumber',
        companyParkingCardPlateNumber: 'carRegistrationNumber',
        carContactPersonEmail: 'companyParkingCardContactPersonEmail',
        companyParkingCardUserFirstName: 'carVisitorFirstName',
        companyParkingCardUserLastName: 'carVisitorLastName'
    };

    ngOnInit() {
        super.ngOnInit();
        this.isUpdate = true;

        // populate data into form
        for (const data of this.request.data) {
            if (data.fieldType !== 'binary') {
                // treat 'string' field
                const formFieldName = this.fieldCodeToFormFieldName[data.code];
                this.form.controls[formFieldName].setValue(data.fieldValue);
            } else {
                // treat files
                this.files[data.code] = data;
            }
        }
    }

    hasFile(fileCode: string): boolean {
        return this.files[fileCode];
    }

    download(dataCode: string) {
        this.requestService.downloadFileByCode(this.request.id, dataCode, this.files[dataCode].fieldValue,
            this.files[dataCode].fieldFileType).subscribe();
    }

    onSubmit() {
        this.loading = true;

        const request = this.extractRequest();

        const formData = new FormData();
        formData.append('request', new Blob([JSON.stringify(request)], { type: 'application/json' }));

        // treat green card file
        let greenCardFileValue = this.actionGreenCardFile;
        if (greenCardFileValue === 'replace' || !this.hasFile(this.greenCardCode)) {
            greenCardFileValue = this.form.get('insurance_certificate').value;
        }
        formData.append(this.greenCardCode, greenCardFileValue);

        // treat user proof file
        let userProofFileValue = this.actionUserProofFile;
        if (userProofFileValue === 'replace' || !this.hasFile(this.userProofCode)) {
            userProofFileValue = this.form.get('car_user_proof').value;
        }
        formData.append(this.userProofCode, userProofFileValue);

        this.requestService.updateRequestWithFileUploads(formData, this.isCompany ? 'companyParkingCard' : 'citizenParkingCard')
            .subscribe(success => {
                this.loading = false;
                if (success) {
                    this.router.navigate(['/myrequests']);
                    this.alertService.success('Demande de modification de carte de parking bien envoyé');
                } else {
                    this.alertService.error('Échec dans la demande de modification de carte de parking');
                }
            });

    }

}
