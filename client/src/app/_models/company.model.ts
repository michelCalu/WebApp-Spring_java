import { Address } from './address.model';

export class Company {
  companyNb: string;
  vatNb: string;
  address = new Address();
  legalForm: string;
  contactPerson: number;
  companyName: string;
  status: string;
}
