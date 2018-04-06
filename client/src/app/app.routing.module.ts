import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './_guards/index';
import { CreateUserComponent } from './createuser/index';
import { HomeComponent } from './home/index';
import { LoginComponent } from './login/index';
import { ShowNewComponent } from './shownewpeople/shownewpeople.component';

const appRoutes: Routes = [
	{ path: '', component: HomeComponent, canActivate: [AuthGuard]},
/*	{
		path: 'home', component: HomeComponent,
		children: [
			//{ path: 'shownewpeople', component: ShowNewComponent, canActivate: [AuthGuard] },
			{ path: 'createuser', component: CreateUserComponent}
		]
	}*/
	/*{ path: '', component: HomeComponent, canActivate: [AuthGuard]},*/
	//{ path: '', component: HomeComponent },
	{ path: 'home', component: HomeComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'createuser', component: CreateUserComponent },

	{ path: '**', redirectTo: '/home', pathMatch: 'full'}
];

//export const AppRoutingModule = RouterModule.forRoot(appRoutes);
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
