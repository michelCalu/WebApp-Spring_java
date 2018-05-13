import { Municipality } from './municipality.model';
import { Employee } from './employee.model';
import { Address } from './address.model';

export class Department {
  id: number;
  name: string;
  address: Address;
  manager: Employee;
  email: string;
  phoneNumber: string;
  municipality: Municipality;
}
