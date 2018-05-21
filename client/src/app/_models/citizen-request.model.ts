import { RequestField } from './request-field.model';
import { Employee } from './employee.model';
import { Citizen } from './citizen.model';
import { Company } from './company.model';

export class CitizenRequest {
    assignee: Employee;
    citizen: Citizen;
    id: number;
    municipalityRef: string;
    status: string;
    systemRef: string;
    typeDescription: string;
    userRef: string;
    data: Array<RequestField> = [];
    department: any; // department
    type: any;
    company: Company;

}
