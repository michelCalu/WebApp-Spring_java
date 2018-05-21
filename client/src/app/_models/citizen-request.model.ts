import { RequestField } from './request-field.model';
import { Employee } from './employee.model';
import { Citizen } from './citizen.model';

export class CitizenRequest {
    assignee: Employee;
    citizen: Citizen;
    companyNb: string;
    id: number;
    municipalityRef: string;
    status: string;
    systemRef: string;
    typeDescription: string;
    userRef: string;
    data: Array<RequestField> = [];
    department: any; // department
    type: any;

}
