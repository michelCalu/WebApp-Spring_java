import { Component, Input, OnInit } from '@angular/core';
import { CitizenRequest  } from '../_models';
import { RequestService } from '../_services';

@Component({
    selector: 'parking-request-data',
    templateUrl: 'parking-request-data.component.html'
})

export class ParkingRequestDataComponent implements OnInit {

    @Input()
    request: CitizenRequest;

    carData = new Map<string, Object>();
    carFilesData = new Map<string, Object>();

    constructor(private requestService: RequestService) { }

    ngOnInit(): void {
        for (const field of this.request.data) {
            if (field.fieldType !== 'binary') {
                this.carData[field.code] = field.fieldValue;
            } else {
                this.carFilesData[field.code] = field;
            }
        }
    }

    downloadFile(dataCode: string) {
        const fileData = this.carFilesData[dataCode];
        if (fileData) {
            this.requestService.downloadFileByCode(this.request.id, dataCode, fileData.fieldValue, fileData.fieldFileType).subscribe();
        }
    }

}
