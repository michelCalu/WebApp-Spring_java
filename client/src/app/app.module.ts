import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
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
import { CreateEmployeeComponent } from './create_employee';
import { RegisterComponent } from './register/index';
import { TranslateModule, TranslateLoader} from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ParkingCardCreationComponent } from './parking-card-creation';
import { EmployeeDashboardComponent } from './employee_dashboard';
import { RequestDetailComponent } from './request-detail';
import { CitizenDetailComponent } from './citizen-detail';
import { NationalityRequestDataComponent } from './nationality-request-data/nationality-request-data.component';
import { ParkingRequestDataComponent } from './parking-request-data';
import { ValidateCitizensComponent } from './validate_citizens/validate_citizens.component';
import { NrnDetailsComponent } from './nrn-details';

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

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
    EmployeeDashboardComponent,
    NewRequestComponent,
    CreateEmployeeComponent,
    NationalityCertificateCreationComponent,
    ParkingCardCreationComponent,
    RegisterComponent,
    RequestDetailComponent,
    CitizenDetailComponent,
    NationalityRequestDataComponent,
    ParkingRequestDataComponent,
    ValidateCitizensComponent,
    NrnDetailsComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
    })
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
