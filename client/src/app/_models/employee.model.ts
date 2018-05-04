import { Address } from "./address.model";


export class Employee {
  id: number;
  firstName: string;
  lastName: string;
  address = new Address();
  mail: string;
  phone: string;
  nationalRegisterNb: string;
  birthdate: Date;
  password: string; // for creation only (when id is null)

  accountNumber: string;
  arrivalDate: Date;
  gender: string;
  civilStatus: string;
  dependentChildren = 0;
  dependentPeople = 0;

}
