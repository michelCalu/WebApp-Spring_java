import { Address } from "./address.model";


export class Citizen {
  id: number;
  firstName: string;
  lastName: string;
  address = new Address();
  mail: string;
  phone: string;
  nationalRegisterNb: string;
  birthdate: Date;
  activated: boolean;
}
