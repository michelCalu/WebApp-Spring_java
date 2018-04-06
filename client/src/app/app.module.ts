import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AlertService, AuthenticationService, PeopleService } from './_services/index';
import { AlertComponent } from './_directives/index';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { AuthGuard } from './_guards/index';
import { AuthService } from './_guards/index';
import { CreateUserComponent } from './createuser/createuser.component';
import { HomeComponent } from './home/index';
import { JwtInterceptor } from './_helpers/index';
import { LoginComponent } from './login/index';
import { MyFoldersComponent } from './myfolders/index';
import { MyProfileComponent } from './myprofile/index';
import { MyRequestsComponent } from './myrequests/index';
import { NewRequestComponent } from './newrequest/index';
import { RegisterComponent } from './register/index';
import { ShowNewComponent } from './shownewpeople/shownewpeople.component';

@NgModule({
  declarations: [
    AlertComponent,
    AppComponent,
    CreateUserComponent,
    HomeComponent,
    LoginComponent,
    MyFoldersComponent,
    MyProfileComponent,
    MyRequestsComponent,
    NewRequestComponent,
    RegisterComponent,
    ShowNewComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthGuard,
    AuthService,
    AlertService,
    AuthenticationService,
    PeopleService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
