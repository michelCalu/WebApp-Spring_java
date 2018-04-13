import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AlertService, AuthenticationService, CitizenService, MockAuthService, RequestService } from './_services/index';
import { AlertComponent } from './_directives/index';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { AuthGuard } from './_guards/index';
import { CreateUserComponent } from './createuser/createuser.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/index';
import { JwtInterceptor } from './_helpers/index';
import { LoginComponent } from './login/index';
import { MyFoldersComponent } from './myfolders/index';
import { MyProfileComponent } from './myprofile/index';
import { MyRequestsComponent } from './myrequests/index';
import { NationalityCertificateCreationComponent} from './nationality-certificate-creation';
import { NewRequestComponent } from './newrequest/index';
import { RegisterComponent } from './register/index';
// import { ShowNewComponent } from './shownewcitizen/shownewcitizen.component';

@NgModule({
  declarations: [
    AlertComponent,
    AppComponent,
    CreateUserComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    MyFoldersComponent,
    MyProfileComponent,
    MyRequestsComponent,
    NewRequestComponent,
    NationalityCertificateCreationComponent,
    RegisterComponent,
    //ShowNewComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthGuard,
    MockAuthService,
    AlertService,
    AuthenticationService,
    CitizenService,
    RequestService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
