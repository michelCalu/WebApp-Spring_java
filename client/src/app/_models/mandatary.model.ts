import { Company } from './company.model';
import { Citizen } from './citizen.model';

export class Mandatary {
  mandatoryID: number;
  citizen = new Citizen();
  company = new Company();
  role: string;
}
