import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CitizenRequest, Company } from '../_models';
import { RequestService, AuthenticationService } from '../_services';

@Component({
    selector: 'parking-request-data',
    templateUrl: 'parking-request-data.component.html'
})

export class ParkingRequestDataComponent implements OnChanges, OnInit {

    @Input()
    request: CitizenRequest;

    carData = new Map<string, Object>();
    carFilesData = new Map<string, Object>();
    company: Company;

    constructor(private requestService: RequestService, private authService: AuthenticationService) { }

    ngOnInit() {
        this.company = this.authService.getCurrentCompany();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['request']) {
            for (const field of this.request.data) {
                if (field.fieldType !== 'binary') {
                    this.carData[field.code] = field.fieldValue;
                } else {
                    this.carFilesData[field.code] = field;
                }
            }
        }
    }

    downloadFile(dataCode: string) {
        const fileData = this.carFilesData[dataCode];
        if (fileData) {
            this.requestService.downloadFileByCode(this.request.id, dataCode, fileData.fieldValue, fileData.fieldFileType).subscribe();
        }
    }

    hasGreenCardFile(): boolean {
        const greenCardCode = this.company ? 'companyParkingCardGreenCard' : 'citizenParkingCardGreenCard';
        return this.carFilesData[greenCardCode] && this.carFilesData[greenCardCode]['fieldFile'];
    }

    hasUserProofFile(): boolean {
        const userProofCode = this.company ? 'companyParkingCardVisitorProof' : 'citizenParkingCardUserProof';
        return this.carFilesData[userProofCode] && this.carFilesData[userProofCode]['fieldFile'];
    }

}
