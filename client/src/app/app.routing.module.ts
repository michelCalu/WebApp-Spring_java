import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './_guards';
import { CreateUserComponent } from './createuser';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { MyFoldersComponent } from './myfolders';
import { MyProfileComponent } from './myprofile';
import { MyRequestsComponent } from './myrequests';
import { NewRequestComponent } from './newrequest';
import { NationalityCertificateCreationComponent } from './nationality-certificate-creation';
import { ParkingCardCreationComponent } from './parking-card-creation';
import { EmployeeDashboardComponent } from './employee_dashboard';
import { CreateEmployeeComponent } from './create_employee';
import { ValidateCitizensComponent } from './validate_citizens/validate_citizens.component';

const appRoutes: Routes = [
  // routes protected with AuthGuard
  { path: '', canActivate: [AuthGuard], children: [
      { path: '', component: HomeComponent },
      { path: 'home', component: HomeComponent },
      { path: 'myfolders', component: MyFoldersComponent },
      { path: 'myprofile', component: MyProfileComponent },
      { path: 'myrequests', component: MyRequestsComponent },
      { path: 'newrequest', component: NewRequestComponent },
      { path: 'employee_dashboard', component: EmployeeDashboardComponent },
      { path: 'create_employee', component: CreateEmployeeComponent },
      { path: 'newrequest/nationality', component: NationalityCertificateCreationComponent},
      { path: 'newrequest/parking', component: ParkingCardCreationComponent},
      { path: 'validate_citizens', component: ValidateCitizensComponent }

    ] },
  // unprotected routes:
  { path: 'login', component: LoginComponent },
  { path: 'createuser', component: CreateUserComponent },
  { path: '**', redirectTo: '/home', pathMatch: 'full'}

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
