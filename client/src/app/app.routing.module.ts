import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ShowNewComponent } from './shownewpeople/shownewpeople.component';
import { CreateUserComponent } from './createuser/createuser.component';

const routes: Routes = [
  { path: 'shownewpeople', component: ShowNewComponent },
  { path: 'createuser', component: CreateUserComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
