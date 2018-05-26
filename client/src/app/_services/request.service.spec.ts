import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { RequestService, AlertService } from '../_services';
import { CitizenRequest, Citizen } from '../_models' ;
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateService } from '@ngx-translate/core';

describe('Service : Employee', () => {
  
  let injector: TestBed;
  let service: RequestService;
  let httpMock: HttpTestingController;
  
   /*
   * Mocking TranslateService, because its dependency TranslateStore cannot be regularly imported for some reason. 
   */
  class MockTranslateService {};

  beforeEach(() => { 
   TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [RequestService, AlertService, {provide: TranslateService, useClass: MockTranslateService}]
    }).compileComponents;
    
    injector = getTestBed();
    service = injector.get(RequestService);
    httpMock = injector.get(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });
  
  it('acceptAction should trigger patch request', () => {
    let citizenRequest = new CitizenRequest();
    citizenRequest.id = 5;
    service.performAction(citizenRequest, 'accept',{comment: 'monCommentaire'}).subscribe(res => expect(res).toBeTruthy());
    const req = httpMock.expectOne('/requests');
    expect(req.request.method).toBe("PATCH"); 
    req.flush(citizenRequest);
  });
  
});