import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './_guards';
import { CreateCompanyComponent } from './createcompany';
import { CreateUserComponent } from './createuser';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
// import { MyFoldersComponent } from './myfolders';
// import { MyProfileComponent } from './myprofile';
import { MyCompaniesComponent } from './mycompanies';
import { MyRequestsComponent } from './myrequests';
import { NewRequestComponent } from './newrequest';
import { NationalityCertificateCreationComponent } from './nationality-certificate-creation';
import { ParkingCardCreationComponent } from './parking-card-creation';
import { EmployeeDashboardComponent } from './employee_dashboard';
import { CreateEmployeeComponent } from './create_employee';
import { ValidateCitizensComponent } from './validate_citizens/validate_citizens.component';
import { ValidateCompaniesComponent } from './validate_companies/validate_companies.component';

const appRoutes: Routes = [
  // routes protected with AuthGuard
  { path: '', canActivate: [AuthGuard], children: [
      { path: '', component: LoginComponent },
    //   { path: 'home', component: HomeComponent },
    //   { path: 'myfolders', component: MyFoldersComponent },
    //   { path: 'myprofile', component: MyProfileComponent },
      { path: 'createcompany', component: CreateCompanyComponent },
      { path: 'mycompanies', component: MyCompaniesComponent},
      { path: 'myrequests', component: MyRequestsComponent },
      { path: 'newrequest', component: NewRequestComponent },
      { path: 'employee_dashboard', component: EmployeeDashboardComponent },
      { path: 'create_employee', component: CreateEmployeeComponent },
      { path: 'newrequest/nationality', component: NationalityCertificateCreationComponent},
      { path: 'newrequest/parking', component: ParkingCardCreationComponent},
      { path: 'validate_citizens', component: ValidateCitizensComponent },
      { path: 'validate_companies', component: ValidateCompaniesComponent }

    ] },
  // unprotected routes:
  { path: 'login', component: LoginComponent },
  { path: 'createuser', component: CreateUserComponent },
  { path: '**', redirectTo: '/login', pathMatch: 'full'}

];

@NgModule({
	imports: [
	RouterModule.forRoot(appRoutes)
	],
	exports: [
	RouterModule
	],
	declarations: []
})
export class AppRoutingModule { }
