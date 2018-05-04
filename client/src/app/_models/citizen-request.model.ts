import { RequestField } from "./request-field.model";

export class CitizenRequest {
    assignee: any; // employee
    citizen: any; // citizen
    company: any; // company
    id: number;
    municipalityRef: string;
    status: string;
    systemRef: string;
    typeDescription: string;
    userRef: string;
    data: Array<RequestField> = [];
    department: any; // department
    type: any;

    // constructor(){
    //   this.data = [];
    // }
}
