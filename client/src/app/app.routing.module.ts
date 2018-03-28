import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/index';
import { ShowNewComponent } from './shownewpeople/shownewpeople.component';
import { CreateUserComponent } from './createuser/index';
import { AuthGuard } from './_guards/index';

const appRoutes: Routes = [
	{ path: '', redirectTo: '/home', pathMatch: 'full'},
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
	{ path: 'shownewpeople', component: ShowNewComponent },
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
