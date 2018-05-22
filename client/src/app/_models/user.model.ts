export class User {
  id: number;
  token: string;
  type: string; // employee or citizen
  roles: string[]; // {USER,ADMIN,OFFICER}
}
